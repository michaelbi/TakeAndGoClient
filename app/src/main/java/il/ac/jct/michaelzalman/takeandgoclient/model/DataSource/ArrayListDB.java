package il.ac.jct.michaelzalman.takeandgoclient.model.DataSource;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import il.ac.jct.michaelzalman.takeandgoclient.model.backend.IDBManager;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.TakeAndGoConsts;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.*;


/**
 * DB implemented with ArrayLists
 */


public class ArrayListDB  implements IDBManager {
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Car> cars = new ArrayList<>();
    private static ArrayList<CarModel> carModels = new ArrayList<>();
    private static ArrayList<Branch> branches = new ArrayList<>();






    //region Description static incremental id's
    private static int CAR_INDEX=10000;
    private static int BRANCH_INDEX=20000;
    private static int CAR_MODEL_INDEX=30000;

    public static int getCarId() {
        return CAR_INDEX++;
    }

    public static int getCarModelId() {
        return BRANCH_INDEX++;
    }

    public static int getBranchId() {
        return CAR_MODEL_INDEX++;
    }

    //endregion


    //region Description getters
    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Car> getCars() {
        return cars;
    }

    public static ArrayList<CarModel> getCarModels() {
        return carModels;
    }

    public static ArrayList<Branch> getBranches() {
        return branches;
    }
    //endregion

    //region Description IDBManager override methods
    @Override
    public boolean isClientExist(ContentValues client) {
        if (clients.isEmpty())
            return false;
        for (Client c : clients) {
            if (c.getId() == client.getAsString(TakeAndGoConsts.ClientConst.ID))
                return true;
        }
        return false;
    }

    @Override
    public void addClient(ContentValues client) throws Exception {
        if (isClientExist(client))
            throw new Exception("trying to add client that allready exist in DB.");
        clients.add(TakeAndGoConsts.ContentValuesToClient(client));

    }

    @Override
    public void addCarModel(ContentValues carModel) throws Exception {
        if (isCarModelExist(carModel))
            throw new Exception("trying to add car model that allready exist in DB.");
        carModel.put(TakeAndGoConsts.CarConst.ID,getCarModelId());
        carModels.add(TakeAndGoConsts.ContentValuesToCarModel(carModel));
    }

    @Override
    public void addCar(ContentValues car) throws Exception {
//        if (isCarExist(car))
//            throw new Exception("trying to add car that allready exist in DB.");
        car.put(TakeAndGoConsts.CarConst.ID,getCarId());
        cars.add(TakeAndGoConsts.ContentValuesToCar(car));
    }

    @Override
    public void addBranch(ContentValues branch) throws Exception {
        branch.remove(TakeAndGoConsts.BranchConst.ID);
        branch.put(TakeAndGoConsts.BranchConst.ID, getBranchId());
        branches.add(TakeAndGoConsts.ContentValuesToBranch(branch));
    }



    @Override
    public List<Client> getAllClients() {
        return clients;
    }

    @Override
    public List<CarModel> getAllCarModels() {
        return carModels;
    }

    @Override
    public List<Branch> getAllBranchs() {
        return branches;
    }

    @Override
    public List<Car> getAllCars() {
        return cars;
    }
    //endregion

    //region Description private existance methods
    private boolean isCarModelExist(ContentValues carModel) {
        if (carModels.isEmpty())
            return false;
        for (CarModel c : carModels) {
            if (c.getId() == carModel.get(TakeAndGoConsts.CarModelConst.ID))
                return true;
        }
        return false;
    }

        private boolean isBranchExist(ContentValues branch) {
        if(branches.isEmpty() || Integer.parseInt(branch.get(TakeAndGoConsts.CarModelConst.ID).toString())==0)
            return false;
        for (Branch b :branches) {
            if(b.getId() == Integer.parseInt(branch.get(TakeAndGoConsts.CarModelConst.ID).toString()))
                return true;
        }
        return false;
    }
    private boolean isCarExist(ContentValues car) {
        if (cars.isEmpty())
            return false;
        for (Car c : cars) {
            if (c.getId() ==  car.get(TakeAndGoConsts.CarConst.ID))
                return true;
        }
        return false;
    }
    //endregion

}
