package com.eucalyptus.blockstorage.ceph;

import java.util.List;

import org.apache.log4j.Logger;

import com.eucalyptus.blockstorage.ceph.entities.CephRbdInfo;
import com.google.common.base.Strings;

public class CanonicalRbdObject {
  private static final Logger LOG = Logger.getLogger(CanonicalRbdObject.class);

  private final String pool;
  private final String image;
  private final String snapshot;

  public String getPool() {
    return pool;
  }

  public CanonicalRbdObject withPool(String pool) {
    return new CanonicalRbdObject( pool, this.image, this.snapshot );
  }

  public String getImage() {
    return image;
  }

  public CanonicalRbdObject withImage(String image) {
    return new CanonicalRbdObject( this.pool, image, this.snapshot );
  }

  public String getSnapshot() {
    return snapshot;
  }

  public CanonicalRbdObject withSnapshot(String snapshot) {
    return new CanonicalRbdObject( this.pool, this.image, snapshot );
  }

  public CanonicalRbdObject( ) {
    this( null, null, null );
  }

  public CanonicalRbdObject( final String pool, final String image, final String snapshot ) {
    this.pool = pool;
    this.image = image;
    this.snapshot = snapshot;
  }

  @Override
  public String toString() {
    return "CanonicalRbdObject [pool=" + pool + ", image=" + image + ", snapshot=" + snapshot + "]";
  }

  public String toCanonicalString() {
    String canonicalId = new String();

    if (!Strings.isNullOrEmpty(this.pool))
      canonicalId = this.pool;
    if (!Strings.isNullOrEmpty(this.image))
      canonicalId += "/" + this.image;
    if (!Strings.isNullOrEmpty(this.snapshot))
      canonicalId += "@" + this.snapshot;

    return canonicalId;
  }

  public static CanonicalRbdObject parse(String canonicalId) {
    try {
      if (!Strings.isNullOrEmpty(canonicalId)) {
        // iqn may be of the form pool/image,pool/image if the snapshot was uploaded pre 4.4. It is always of the form pool/image in 4.4
        String newCanonicalId = canonicalId.contains(",") ? canonicalId.substring(0, canonicalId.indexOf(',')) : canonicalId;

        List<String> poolSplit = CephRbdInfo.POOL_IMAGE_SPLITTER.splitToList(newCanonicalId);
        if (poolSplit != null && poolSplit.size() == 2) {
          CanonicalRbdObject resource = new CanonicalRbdObject().withPool(poolSplit.get(0));
          if (poolSplit.get(1).contains(CephRbdInfo.IMAGE_SNAPSHOT_DELIMITER)) {
            List<String> imageSnapshotSplit = CephRbdInfo.IMAGE_SNAPSHOT_SPLITTER.splitToList(poolSplit.get(1));
            if (imageSnapshotSplit != null && imageSnapshotSplit.size() == 2) {
              return resource.withImage(imageSnapshotSplit.get(0)).withSnapshot(imageSnapshotSplit.get(1));
            } else {
              LOG.warn("Expected single occurence of @ in canonical ID " + newCanonicalId);
              return null;
            }
          } else {
            return resource.withImage(poolSplit.get(1));
          }
        } else {
          LOG.warn("Expected single occurence of / in canonical ID " + newCanonicalId);
          return null;
        }
      } else {
        // Caller may pass in a null ID which may be OK.
        // If not, errors will be handled at a higher level.
        LOG.debug("Invalid canonical ID " + canonicalId);
        return null;
      }
    } catch (Exception e) {
      LOG.warn("Failed to parse " + canonicalId, e);
      return null;
    }
  }
}
