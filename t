[1mdiff --git a/backend/src/main/java/com/mmsl/fiwmoney/controller/StockController.java b/backend/src/main/java/com/mmsl/fiwmoney/controller/StockController.java[m
[1mindex f7fe8da..fa41fc6 100644[m
[1m--- a/backend/src/main/java/com/mmsl/fiwmoney/controller/StockController.java[m
[1m+++ b/backend/src/main/java/com/mmsl/fiwmoney/controller/StockController.java[m
[36m@@ -4,6 +4,7 @@[m [mimport java.util.List;[m
 [m
 import org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.http.ResponseEntity;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.CrossOrigin;[m
 import org.springframework.web.bind.annotation.GetMapping;[m
 import org.springframework.web.bind.annotation.PostMapping;[m
 import org.springframework.web.bind.annotation.RequestBody;[m
[36m@@ -22,7 +23,7 @@[m [mpublic class StockController {[m
     @Autowired[m
     private StockService service;[m
 [m
[31m-    // @CrossOrigin(origins = "http://localhost:9000")[m
[32m+[m[32m    @CrossOrigin(origins = "http://localhost:5173")[m
     @PostMapping(value = "/stock")[m
     public ResponseEntity<StockResult> getStock(@RequestBody StockRequest stock) {[m
         if (stock.getCode() == null) {[m
