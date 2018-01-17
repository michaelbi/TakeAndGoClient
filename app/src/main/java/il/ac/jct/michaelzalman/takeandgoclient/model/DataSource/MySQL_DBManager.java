package il.ac.jct.michaelzalman.takeandgoclient.model.DataSource;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import il.ac.jct.michaelzalman.takeandgoclient.model.backend.IDBManager;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.TakeAndGoConsts;
import il.ac.jct.michaelzalman.takeandgoclient.model.entities.*;


/**
 * Created by מיכאל on 08/12/2017.
 */

public class MySQL_DBManager implements IDBManager {

    private static String WEB_URL = "http://mbitan.vlab.jct.ac.il/DBmanage/";

    @Override
    public boolean isClientExist(ContentValues client) {


        return false;
    }

    @Override
    public void addClient(ContentValues client) throws Exception {

        String message, sub;
        message = PHPtools.POST(WEB_URL + "addClient.php", client);
        try {
            sub = message.substring(1, message.indexOf(' ', 1) - 1);
            Integer.parseInt(sub);
        } catch (Exception e) {
            throw new Exception(message);
        }

    }

    @Override
    public void addCarModel(ContentValues carModel) throws Exception {

        String message, sub;
        message = PHPtools.POST(WEB_URL + "addCarModel.php", carModel);

        try {
            sub = message.substring(1, message.indexOf(' ', 1) - 1);
            Integer.parseInt(sub);
        } catch (Exception e) {
            throw new Exception(message);
        }

    }

    @Override
    public void addCar(ContentValues car) throws Exception {

        String message, sub;
        message = PHPtools.POST(WEB_URL + "addCar.php", car);
        try {
            sub = message.substring(1, message.indexOf(' ', 1) - 1);
            Integer.parseInt(sub);
        } catch (Exception e) {
            throw new Exception(message);
        }
    }

    @Override
    public void addBranch(ContentValues branch) throws Exception {

        String message, sub;
        message = PHPtools.POST(WEB_URL + "addBranch.php", branch);
        try {
            sub = message.substring(1, message.indexOf(' ', 1) - 1);
            Integer.parseInt(sub);
        } catch (Exception e) {
            throw new Exception(message);
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> result = new ArrayList<Client>();
        try {
            String str = PHPtools.GET(WEB_URL + "/getClients.php");
            JSONArray array = new JSONObject(str).getJSONArray("clients");
            for (int i = 0; i < array.length(); i++) {
                result.add(
                        TakeAndGoConsts.ContentValuesToClient(
                                PHPtools.jsonToContentValues(array.getJSONObject(i))
                        ));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CarModel> getAllCarModels() {
        List<CarModel> result = new ArrayList<CarModel>();
        try {
            String str = PHPtools.GET(WEB_URL + "/getCarModels.php");
            JSONArray array = new JSONObject(str).getJSONArray("car_models");
            for (int i = 0; i < array.length(); i++) {
                result.add(
                        TakeAndGoConsts.ContentValuesToCarModel(
                                PHPtools.jsonToContentValues(array.getJSONObject(i))
                        ));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Branch> getAllBranchs() {

        List<Branch> result = new ArrayList<Branch>();
        try {
            String str = PHPtools.GET(WEB_URL + "/getBranches.php");
            JSONArray array = new JSONObject(str).getJSONArray("branches");
            for (int i = 0; i < array.length(); i++) {
                result.add(
                        TakeAndGoConsts.ContentValuesToBranch(
                                PHPtools.jsonToContentValues(array.getJSONObject(i))
                        ));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {

        List<Car> result = new ArrayList<Car>();
        try {
            String str = PHPtools.GET(WEB_URL + "/getCars.php");
            JSONArray array = new JSONObject(str).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                result.add(
                        TakeAndGoConsts.ContentValuesToCar(
                                PHPtools.jsonToContentValues(array.getJSONObject(i))
                        ));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
