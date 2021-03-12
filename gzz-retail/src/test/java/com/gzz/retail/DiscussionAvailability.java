package com.gzz.retail;

public enum DiscussionAvailability {
    ADD_ON_NOT_ENABLED {
        public boolean isAddOnNotAvailable() {
            return true;
        }
    },

    FAILED {
        public boolean isFailed() {
            return true;
        }
    },

    NOT_REQUESTED {
        public boolean isNotRequested() {
            return true;
        }
    },

    REQUESTED {
        public boolean isRequested() {
            return true;
        }
    },

    READY {
        public boolean isReady() {
            return true;
        }
    };

    public boolean isAddOnNotAvailable() {
        return false;
    }

    public boolean isFailed() {
        return false;
    }

    public boolean isNotRequested() {
        return false;
    }

    public boolean isReady() {
        return false;
    }

    public boolean isRequested() {
        return false;
    }
}
