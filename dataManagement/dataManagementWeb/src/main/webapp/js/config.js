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
            "list":"rs/ocwalves/list"
        },
        "walvesassembly":{
            "list":"rs/walvesassembly/list"
        },
        "zfunccheck":{
            "list":"rs/zfunccheck/list"
        },
        "nlcheck":{
            "list":"rs/nlcheck/list"
        },
        "box":{
            "findByCondition":"rs/box/findByCondition"
        },
        "storage":{
            "findByCondition":"rs/storage/findByCondition"
        },
        "delivery":{
            "findByCondition":"rs/delivery/findByCondition"
        },
        "userfunction":{
            "currentFuncRole":"rs/userfunction/currentFuncRole",
            "alldepart":"rs/userfunction/listdepart",
            "allrole":"rs/userfunction/listrole",
            "adduser":"rs/userfunction/adduser",
            "getrole":"rs/userfunction/getrole"
        },
        "updatepassword":{
            "update":"rs/updatepassword/update",
            "checkDepartNo":"rs/updatepassword/checkDepartNo"
        },
        "userCheck":{
            "checkname":"rs/userCheck/checkname"
        },
        "depart": {
            "alluser": "rs/depart/alluser",
            "add": "rs/depart/add",
            "checkNo": "rs/depart/checkNo",
            "checkName": "rs/depart/checkName",
            "departuser": "rs/depart/departuser",
            "findUser": "rs/depart/findUser",
            "uprole": "rs/depart/uprole",
            "list": "rs/depart/list"
        },
        "department": {
            "list": "rs/department/list",
            "add":"rs/department/add",
            "delete":"rs/department/delete",
            "update":"rs/department/update",
            "findDepart":"rs/department/findDepart"
        },
        "enumTable": {
            "meterlist":"rs/enumTable/meterlist",
            "elelist":"rs/enumTable/elelist",
            "misinfolist":"rs/enumTable/misinfolist",
            "cuslist":"rs/enumTable/cuslist",
            "mislist":"rs/enumTable/mislist",
            "add":"rs/enumTable/add",
            "update":"rs/enumTable/update",
            "delete":"rs/enumTable/delete",
            "alllist":"rs/enumTable/alllist",
            "findBySearch":"rs/enumTable/findBySearch"
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
        "dashboard": {
            "mainDeviceList": "rs/device/getMainDeviceList",
            "installationList": "rs/installation/findByCondition",
            "removeList": "rs/remove/findByCondition",
            "preBuryList": "rs/preBury/getPreburyList",
            "contractList": "rs/contract/getContractList",
            "inStockList": "rs/stock_in_sheet/getInStockList",
            "outStockList": "rs/stock_out_sheet/getOutStockList"
        },
        "returnMeter": {
            "add": "rs/returnMeter/add",
            "delete": "rs/returnMeter/delete",
            "update": "rs/returnMeter/update",
            "list": "rs/returnMeter/list",
            "findByCondition": "rs/returnMeter/findByCondition",
            "findBySearch": "rs/returnMeter/findBySearch",
            "findByMap":"rs/returnMeter/findByMap"
        },
        "returnMeterInfo": {
            "add": "rs/returnMeterInfo/add",
            "delete": "rs/returnMeterInfo/delete",
            "update": "rs/returnMeterInfo/update",
            "list": "rs/returnMeterInfo/list",
            "findByCondition": "rs/returnMeterInfo/findByCondition",
            "findBySearch": "rs/returnMeterInfo/findBySearch",
            "stalist":"rs/returnMeterInfo/stalist"
        },
        "graph":{
            "graphYear":"rs/graph/graphYear",
            "graphYears":"rs/graph/graphYears",
            "graphCompareYears":"rs/graph/graphCompareYears"
        },
        "returnCustomAndName":{
            "findCustomAndName":"rs/returnCustomAndName/findCustomAndName"
        },
        "meterAnalysis":{
            "add":"rs/meterAnalysis/add",
            "delete":"rs/meterAnalysis/delete",
            "update":"rs/meterAnalysis/update",
            "list":"rs/meterAnalysis/list",
            "findByCondition":"rs/meterAnalysis/findByCondition",
            "findBySearch":"rs/meterAnalysis/findBySearch"
        },
        "returnValveAndName":{
            "findValveAndName":"rs/returnValveAndName/findValveAndName"
        },
        "boxCodeSearch":{
            "findByCondition":"rs/boxCodeSearch/findByCondition"
        },
        "boxBatch":{
            "getBoxBatchInfo":"rs/boxBatch/getBoxBatchInfo"
        },
        "updateRecord":{
            "add":"rs/updateRecord/add",
            "findByCondition":"rs/updateRecord/findByCondition"
        }
    }
})(jQuery);