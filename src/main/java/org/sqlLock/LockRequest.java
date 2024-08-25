package org.sqlLock;

import java.time.Duration;
import java.util.Objects;

//generic lock request class
public class LockRequest {
    private final String lockId;
    private final String ownerId;
    private final Duration duration;
    private final String tableName;

    public LockRequest(
            String lockId,
            String ownerId,
            Duration duration, String tableName) {
        this.lockId = Objects.requireNonNull(lockId);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.duration = Objects.requireNonNull(duration);
        this.tableName = tableName;
    }

    public LockRequest(
            String lockId,
            String ownerId, String tableName) {
        this(lockId, ownerId, null, tableName);
    }

    public String getLockId() {
        return lockId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LockRequest request = (LockRequest) o;
        return Objects.equals(lockId, request.lockId) &&
                Objects.equals(ownerId, request.ownerId) &&
                Objects.equals(duration, request.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lockId, ownerId, duration);
    }

    @Override
    public String toString() {
        return "LockRequest{" +
                "lockId=" + lockId +
                ", ownerId=" + ownerId +
                ", duration=" + duration +
                '}';
    }
}
