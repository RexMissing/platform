(function($){
    $.URL = {
        "common":{
          "rootPath":"http://www.cseicms.com/"
        },
        "power":{
            "add":"rs/power/add",
            "update":"rs/power/update",
            "delete":"rs/power/delete",
            "list":"rs/power/list"
        },
        "ocwalves":{
            "list":"rs/ocwalves/list",
        },
        "walvesassembly":{
            "list":"rs/walvesassembly/list",
        },
        "user":{
            "add":"rs/user/add",
            "update":"rs/user/update",
            "delete":"rs/user/delete",
            "list":"rs/user/list",
            "getId":"rs/user/getIdByName" ,
            "currentUserId": "rs/user/currentUserId",
            "currentUserInfo":"rs/user/currentUser"
        },
        "authority":{
             "add":"rs/authority/add",
             "update":"rs/authority/update",
             "delete":"rs/authority/delete",
            "list":"rs/authority/list"
        },
        "userAuthority":{
              "add":"rs/userAuthority/add"
        },
        "contract":{
            "add":"rs/contract/add",
            "update":"rs/contract/update",
            "delete":"rs/contract/delete",
            "list":"rs/contract/list",
            "getContractById":"rs/contract/getContractById"
        },
        "customer":{
            "add":"rs/customer/add",
            "update":"rs/customer/update",
            "delete":"rs/customer/delete",
            "list":"rs/customer/list",
            "listCustomerByNameAndLinkman":"rs/customer/listCustomerByNameAndLinkman"
        },
        "supplier":{
            "add":"rs/supplier/add",
            "update":"rs/supplier/update",
            "delete":"rs/supplier/delete",
            "list":"rs/supplier/list",
            "listSupplierByNameAndLinkman":"rs/supplier/listSupplierByNameAndLinkman"
        },
        "installation":{
            "list":"rs/installation/list",
            "listByContractId":"rs/installation/listByContractId",
            "add":"rs/installation/add",
            "update":"rs/installation/update",
            "delete":"rs/installation/delete",
            "findByCondition":"rs/installation/findByCondition",
            "info":"rs/installation/info",
            "upload":"rs/installation/upload"
        },
        "transport":{
            "list":"rs/transport/list",
            "listByContractId":"rs/transport/listByContractId",
            "add":"rs/transport/add",
            "update":"rs/transport/update",
            "delete":"rs/transport/delete",
            "findByCondition":"rs/transport/findByCondition",
            "info":"rs/transport/info",
            "upload":"rs/transport/upload"
        },
        "transportDevice":{
            "list":"rs/transportDevice/list",
            "add":"rs/transportDevice/add",
            "update":"rs/transportDevice/update",
            "delete":"rs/transportDevice/delete",
            "listByTransportId":"rs/transportDevice/listByTransportId"
        },
        "employee":{
            "list":"rs/employee/list",
            "add":"rs/employee/add",
            "update":"rs/employee/update",
            "delete":"rs/employee/delete",
            "getDepartmentById":"rs/employee/getDepartmentById"
        },
        "department":{
            "add":"rs/department/add",
            "list":"rs/department/list",
            "update":"rs/department/update",
            "delete":"rs/department/delete"
        },
        "car_Driver":{
            "add":"rs/car_Driver/add",
            "list":"rs/car_Driver/list",
            "update":"rs/car_Driver/update",
            "delete":"rs/car_Driver/delete",
            "listCar_DriverByNameAndCar_Number":"rs/car_Driver/listCar_DriverByNameAndCar_Number"
        },
        "batch":{
            "add":"rs/batch/add",
            "update":"rs/batch/update",
            "delete":"rs/batch/delete",
            "list":"rs/batch/list",
            "getIdByNumber":"rs/batch/getIdByNumber",
            "findByCondition":"rs/batch/findByCondition"
        },
        "device":{
            "add":"rs/device/add",
            "update":"rs/device/update",
            "delete":"rs/device/delete",
            "list":"rs/device/list",
            "getIdByNumber":"rs/device/getIdByNumber",
            "findByCondition":"rs/device/findByCondition",
            "mainDeviceInfo":"rs/device/mainDeviceInfo"
        },
        "deviceData":{
            "getDeviceData":"rs/deviceData/getDeviceData"
        },
        "deviceType":{
            "list":"rs/deviceType/list",
            "add":"rs/deviceType/add",
            "update":"rs/deviceType/update",
            "delete":"rs/deviceType/delete",
            "getIdByName":"rs/deviceType/getIdByName"
        },
        "stockIn":{
            "list":"rs/stockIn/list",
            "listByContractId":"rs/stockIn/listByContractId",
            "add":"rs/stockIn/add",
            "update":"rs/stockIn/update",
            "delete":"rs/stockIn/delete",
            "findByCondition":"rs/stockIn/findByCondition",
            "info":"rs/stockIn/info",
            "upload":"rs/stockIn/upload"
        },
        "stock_in_sheet":{
            "add":"rs/stock_in_sheet/add" ,
            "list":"rs/stock_in_sheet/list",
            "update":"rs/stock_in_sheet/update",
            "delete":"rs/stock_in_sheet/delete"
        },
        "stockOut":{
            "list":"rs/stockOut/list",
            "listByContractId":"rs/stockOut/listByContractId",
            "add":"rs/stockOut/add",
            "update":"rs/stockOut/update",
            "delete":"rs/stockOut/delete",
            "findByCondition":"rs/stockOut/findByCondition",
            "info":"rs/stockOut/info",
            "upload":"rs/stockOut/upload"
        },
        "stock_out_sheet":{
            "add" :"rs/stock_out_sheet/add" ,
            "list":"rs/stock_out_sheet/list" ,
            "delete": "rs/stock_out_sheet/delete" ,
            "update":"rs/stock_out_sheet/update"
        },
        "selfInspect":{
            "add":"rs/selfInspect/add" ,
            "update":"rs/selfInspect/update",
            "delete":"rs/selfInspect/delete" ,
            "list":"rs/selfInspect/list"
        },
        "remove":{
            "add":"rs/remove/add"  ,
            "update":"rs/remove/update",
            "delete":"rs/remove/delete" ,
            "list":"rs/remove/list",
            "listByContractId":"rs/remove/listByContractId",
            "info":"rs/remove/info",
            "upload":"rs/remove/upload"
        },
        "preBury":{
            "add":"rs/preBury/add"  ,
            "update":"rs/preBury/update",
            "delete":"rs/preBury/delete" ,
            "list":"rs/preBury/list",
            "listByContractId":"rs/preBury/listByContractId",
            "findByCondition":"rs/preBury/findByCondition"
        },
        "bad_Debt_Sheet":{
            "add":"rs/bad_Debt_Sheet/add",
            "update":"rs/bad_Debt_Sheet/update",
            "delete":"rs/bad_Debt_Sheet/delete",
            "list":"rs/bad_Debt_Sheet/list"
        },
        "skill":{
            "add":"rs/skill/add",
            "update":"rs/skill/update",
            "delete":"rs/skill/delete",
            "list":"rs/skill/list",
            "getSkillNameById":"rs/skill/getSkillNameById"
        },
        "storehouse":{
            "add" :"rs/storeHouse/add" ,
            "list":"rs/storeHouse/list" ,
            "delete": "rs/storeHouse/delete" ,
            "update":"rs/storeHouse/update",
            "getIdByNameAndAppId":"rs/storeHouse/getIdByNameAndAppId"
        },
        "dashboard":{
            "mainDeviceList":"rs/device/getMainDeviceList",
            "installationList":"rs/installation/findByCondition",
            "removeList":"rs/remove/findByCondition",
            "preBuryList":"rs/preBury/getPreburyList",
            "contractList":"rs/contract/getContractList",
            "inStockList":"rs/stock_in_sheet/getInStockList",
            "outStockList":"rs/stock_out_sheet/getOutStockList"
        },
        "deviceReport":{
            "deviceAccountReport":"rs/deviceReport/deviceAccountReport",
            "exportDeviceAccountReport":"rs/deviceReport/exportDeviceAccountReport",
            "rentDeviceReport":"rs/deviceReport/rentDeviceReport",
            "exportRentDeviceReport":"rs/deviceReport/exportRentDeviceReport",
            "deviceTrendReport":"rs/deviceReport/deviceTrendReport",
            "exportDeviceTrendReport":"rs/deviceReport/exportDeviceTrendReport"

        }
    }
})(jQuery);