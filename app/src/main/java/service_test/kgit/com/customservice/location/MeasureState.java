package service_test.kgit.com.customservice.location;

import java.util.Map;

public class MeasureState {

    private boolean isRunning;
    private boolean isBinded;
    private boolean isSaveToRepoEnabled;
    private Map<String, Object> metadata;
    private boolean permissionGranted;
    private int locationsStored;

    public MeasureState(boolean isRunning, boolean isBinded, boolean isSaveToRepoEnabled,
                        Map<String, Object> metada, boolean permissionStatus, int locationsStored){
        this.isRunning = isRunning;
        this.isBinded = isBinded;
        this.isSaveToRepoEnabled = isSaveToRepoEnabled;
        this.metadata = metada;
        this.permissionGranted = permissionStatus;
        this.locationsStored = locationsStored;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isBinded() {
        return isBinded;
    }

    public boolean isSaveToRepoEnabled() {
        return isSaveToRepoEnabled;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public boolean isPermissionGranted() {
        return permissionGranted;
    }

    public int getLocationsStored() {
        return locationsStored;
    }

    @Override
    public String toString() {
        return "MeasureState{" +
                "isRunning=" + isRunning +
                ", isBinded=" + isBinded +
                ", isSaveToRepoEnabled=" + isSaveToRepoEnabled +
                ", metadata=" + metadata +
                ", permissionGranted=" + permissionGranted +
                ", locationsStored=" + locationsStored +
                '}';
    }
}
