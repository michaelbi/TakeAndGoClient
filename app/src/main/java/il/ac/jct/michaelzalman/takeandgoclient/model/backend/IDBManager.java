package il.ac.jct.michaelzalman.takeandgoclient.model.backend;

import android.content.ContentProvider;
import android.content.ContentValues;

import java.util.List;

import il.ac.jct.michaelzalman.takeandgoclient.model.entities.*;

/**
 * IBackent to handle data sources
 */

public interface IDBManager {
    boolean isClientExist(ContentValues client);
    void addClient(ContentValues client) throws Exception;
    void addCarModel (ContentValues carModel) throws Exception;
    void addCar(ContentValues car) throws Exception;
    void addBranch(ContentValues content) throws Exception;
    List<Client> getAllClients();
    List<CarModel> getAllCarModels();
    List<Branch> getAllBranchs();
    List<Car> getAllCars();

    void updateCarKilometers(ContentValues kilometers);
    List<Car> getAvailableCars();
    List<Car> getAvailableCarsForBranch(ContentValues branchID);
    List<Order> getUnclosedOrders();
    void addOrder(ContentValues order) throws Exception;
    void closeOrder(ContentValues kilometers);
    boolean loginCheck(ContentValues userAndPass) throws Exception;



}
