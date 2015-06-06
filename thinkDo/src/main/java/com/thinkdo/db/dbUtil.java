package com.thinkdo.db;

import com.thinkdo.entity.CustomerModel;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.entity.TestVehicleInfoModel;

import java.util.List;

public class dbUtil {

    /**
     * @param manId    车厂Id
     * @param pinyin   索引
     * @param dbIndex  数据库索引
     * */
    public List<String> queryAllCar(String manId, String pinyin, int dbIndex) {
        return dbIndex == GloVariable.stadb
                ? new VehicleDbUtil().queryAllCar(manId, pinyin)
                : new CustomDbUtil().queryAllCar(manId, pinyin);

    }



    public ReferData queryReferData(String vehicleId, int dbIndex) {
        return dbIndex == GloVariable.stadb
                ? new VehicleDbUtil().queryReferData(vehicleId)
                : new CustomDbUtil().queryReferData(vehicleId);
    }

    public SpecialParams querySpecParam(String vehicleId) {
        return new VehicleDbUtil().querySpecParam(vehicleId);
    }


    public boolean addCustomer(CustomerModel data) {
        if (data == null) return false;
        CustomDbUtil util = new CustomDbUtil();
        if (data.getId() == null) {
            String id = util.getInsertCustomerId();
            if (id == null) return false;
            data.setId(id);
        }

        data.initEmptyValue();
        return util.insertCustomer(data);
    }


    public boolean addTestVehicleInfo(TestVehicleInfoModel data) {
        if (data == null || data.getCustomerId() == null) return false;
        CustomDbUtil util = new CustomDbUtil();
        if (data.getTestSerial() == null) {
            String series = util.getInsertTestSeries(data.getCustomerId());
            if (series == null) return false;
            data.setTestSerial(series);
        }
        data.initEmptyValue();
        return util.insertTestVehicleInfo(data);
    }

    public boolean addTestVehicleData(ReferData data, String customerId, String plateNo, String testSeries, String date) {
        if (data == null || customerId == null || plateNo == null || testSeries == null || date == null)
            return false;
        CustomDbUtil util = new CustomDbUtil();
        data.initEmptyValue();
        return util.insertTestVehicleData(data, customerId, plateNo, testSeries, date);
    }
}
