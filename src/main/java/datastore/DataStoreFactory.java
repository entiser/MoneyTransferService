package datastore;

public abstract class DataStoreFactory {
  public enum DataStores {
    H2(1, "H2") {
      @Override
      public DataStore getStoreImpl() {
        return new H2DataStore();
      }
    },
    DEFAULT(0, "H2") {
      @Override
      public DataStore getStoreImpl() {
        return new H2DataStore();
      }
    };

    private int id;
    private String name;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    DataStores(int id, String name) {
      this.id = id;
      this.name = name;
    }
    public static DataStores getDataStoreByName(String name) {
      for (DataStores dataStore : DataStores.values()) {
        if(dataStore.getName().equalsIgnoreCase(name))
          return dataStore;
      }
      return DEFAULT;
    }
    public abstract DataStore getStoreImpl();
  }

//  public abstract Da addTestData();

}
