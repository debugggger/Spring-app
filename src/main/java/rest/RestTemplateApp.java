package rest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Scanner;

public class RestTemplateApp {
    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);
        String token = "";
        System.out.print("help - show commands\n");
        while (true) {
            System.out.print("Input a request: ");
            String str = in.nextLine();

            try{
                if (str.equals(("help"))){
                    System.out.print("singin - authorization \n" +
                            "singout - cancel authorization \n" +
                            "goods - find all goods \n" +
                            "findGood - find good by id \n" +
                            "updateGood - update good`s priority with entered id \n" +
                            "deleteGood - delete good by id \n" +
                            "goodsByName - find goods with entered name \n" +
                            "addGood - add good \n" +
                            "sales - find all sales \n" +
                            "findSale - find sale by id \n" +
                            "deleteSale - delete sale by id \n" +
                            "executeSale - delete sale, change count of goods on warehouses \n" +
                            "addSale - add sale \n" +
                            "salesByName - find sale with entered name \n" +
                            "warehouse1 - find all warehouse1 \n" +
                            "findWarehouse1 - find warehouse1 by id \n" +
                            "updateWarehouse1 - update count of good on warehouse1 \n" +
                            "deleteWarehouse1 - delete warehouse1 by id \n" +
                            "addWarehouse1 - add warehouse1 \n" +
                            "warehouse1ByName - find warehouse1 with entered name \n" +
                            "warehouse1PriorityUnder - find warehouse1 with priority under entered number\n" +
                            "warehouse2 - find all warehouse2 \n" +
                            "findWarehouse2 - find warehouse2 by id \n" +
                            "deleteWarehouse2 - delete warehouse2 by id \n" +
                            "addWarehouse2 - add warehouse2 \n" +
                            "warehouse2ByName - find warehouse2 with entered name \n" +
                            "updateWarehouse2 - update count of good on warehouse2 \n" +
                            "warehouse2Priority - find warehouse2 with priority equals entered number \n"
                    );
                }

                if (str.equals("singin")){
                    System.out.print("Input name: ");
                    String name = in.nextLine();
                    System.out.print("Input password: ");
                    String pass = in.nextLine();

                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/auth/singin";
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> request = new HttpEntity<>("{\n" +
                            "    \"userName\": \"" + name + "\",\n" +
                            "    \"password\": \"" + pass + "\"\n" +
                            "}", headers);
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);

                    Object obj = new JSONParser().parse(responseEntity.getBody());
                    JSONObject jo = (JSONObject) obj;
                    token = (String) jo.get("token");
                    if (responseEntity.getStatusCode().toString().equals("200 OK")){
                        System.out.println("Auth successful");
                    }
                }

                if (str.equals("singout")){
                    if (token != ""){
                        token = "";
                        System.out.println("Auth canceled");
                    }
                    else
                        System.out.println("You not authorised");
                }

                if (str.equals("goods")){
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/goods";
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+---------+--------+%n");
                        System.out.format("|id  |name     |priority|%n");
                        System.out.format("+----+---------+--------+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            System.out.format("|%4d|%9s|%8f|%n", jo.get("id"), jo.get("name"), jo.get("priority"));
                        }
                        System.out.format("+----+---------+--------+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("findGood")){
                    System.out.print("Input id: ");
                    String id = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/findGood/" + id;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    Object obj = new JSONParser().parse(responseEntity.getBody());
                    JSONObject jo = (JSONObject) obj;
                    if (jo.size() > 0) {
                        System.out.format("+----+---------+--------+%n");
                        System.out.format("|id  |name     |priority|%n");
                        System.out.format("+----+---------+--------+%n");
                        System.out.format("|%4d|%9s|%8f|%n", jo.get("id"), jo.get("name"), jo.get("priority"));
                        System.out.format("+----+---------+--------+%n");
                    }
                }

                if (str.equals("goodsByName")){
                    System.out.print("Input name: ");
                    String name = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/goodsByName/" + name;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+---------+--------+%n");
                        System.out.format("|id  |name     |priority|%n");
                        System.out.format("+----+---------+--------+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            System.out.format("|%4d|%9s|%8f|%n", jo.get("id"), jo.get("name"), jo.get("priority"));
                        }
                        System.out.format("+----+---------+--------+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("updateGood")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        System.out.print("Input new priority: ");
                        String priority = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/updateGood/" + id +"/" + priority;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful update good");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("deleteGood")){
                    if (token != "") {
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/deleteGood/" + id;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful delete good");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("addGood")){
                    if (token != ""){
                        System.out.print("Input name: ");
                        String name = in.nextLine();
                        System.out.print("Input priority: ");
                        String priority = in.nextLine();

                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/addGood";
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>("{\n" +
                                "    \"name\": \"" + name + "\",\n" +
                                "    \"priority\": \"" + priority + "\"\n" +
                                "}", headers);

                        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request,  String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful add good");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("sales")){
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/sales";
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        System.out.format("|id  |good id|count|date                        |%n");
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%19s|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"), jo.get("createDate"));
                        }
                        System.out.format("+----+-------+-----+----------------------------+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("addSale")){
                    if (token != ""){
                        System.out.print("Input count: ");
                        String count = in.nextLine();
                        System.out.print("Input good ID: ");
                        String goodId = in.nextLine();

                        RestTemplate restTemplateGood = new RestTemplate();
                        String urlGood = "http://localhost:8080/bt/findGood/" + goodId;
                        ResponseEntity<String> responseEntityGood = restTemplateGood.getForEntity(urlGood, String.class);
                        String good = responseEntityGood.getBody();

                        Instant instant1 = Instant.now();
                        Timestamp sqlTimestamp = Timestamp.from(instant1);
                        String dateTime = sqlTimestamp.toString() + "+0000";
                        String date = dateTime.substring(0, 10);
                        String time = date + "T";
                        time += dateTime.substring(11);
                        int isCountOk = 0;
                        long goodCount = 0;

                        while (isCountOk == 0){
                            long sumCount = 0;
                            RestTemplate restTemplateWarehouse1 = new RestTemplate();
                            String urlWarehouse1 = "http://localhost:8080/bt/warehouse1ByGoodId/" + goodId;
                            ResponseEntity<String> responseEntityWarehouse1 = restTemplateWarehouse1.getForEntity(urlWarehouse1, String.class);


                            JSONArray array = (JSONArray) new JSONParser().parse(responseEntityWarehouse1.getBody());
                            if (array.size() > 0) {
                                for (int i = 0; i < array.size(); i++){
                                    Object obj = array.get(i);
                                    JSONObject jo = (JSONObject) obj;
                                    goodCount += (long) jo.get("goodCount");
                                    sumCount += goodCount;
                                }
                            }

                            RestTemplate restTemplateWarehouse2 = new RestTemplate();
                            String urlWarehouse2 = "http://localhost:8080/bt/warehouse2ByGoodId/" + goodId;
                            ResponseEntity<String> responseEntityWarehouse2 = restTemplateWarehouse2.getForEntity(urlWarehouse2, String.class);

                            array = (JSONArray) new JSONParser().parse(responseEntityWarehouse2.getBody());
                            if (array.size() > 0){
                                for (int i = 0; i < array.size(); i++){
                                    Object obj = array.get(i);
                                    JSONObject jo = (JSONObject) obj;
                                    goodCount = (long) jo.get("goodCount");
                                    sumCount += goodCount;
                                }
                            }


                            int iCount = Integer.parseInt(count);

                            if (iCount <= sumCount) {
                                break;
                            }
                            else {
                                System.out.print("Not enough goods. Input new count: ");
                                count = in.nextLine();
                            }
                        }

                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/addSale";
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>("{\n" +
                                "    \"good\": " + good + ",\n" +
                                "    \"goodCount\": \"" + count + "\",\n" +
                                "    \"createDate\": \"" + time + "\"\n" +
                                "}", headers);

                        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request,  String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful add sale");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("findSale")){
                    System.out.print("Input id: ");
                    String id = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/findSale/" + id;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    Object obj = new JSONParser().parse(responseEntity.getBody());
                    JSONObject jo = (JSONObject) obj;
                    if (jo.size() > 0) {
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        System.out.format("|id  |good id|count|date                        |%n");
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        JSONObject joGood = (JSONObject) jo.get("good");
                        System.out.format("|%4d|%7d|%5d|%19s|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"), jo.get("createDate"));
                        System.out.format("+----+-------+-----+----------------------------+%n");
                    }
                }

                if (str.equals("salesByName")){
                    System.out.print("Input name: ");
                    String name = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/salesByName/" + name;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        System.out.format("|id  |good id|count|date                        |%n");
                        System.out.format("+----+-------+-----+----------------------------+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%19s|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"), jo.get("createDate"));
                        }
                        System.out.format("+----+-------+-----+----------------------------+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("deleteSale")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/deleteSale/" + id;

                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful delete sale");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("executeSale")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        System.out.print("Input warehouse priority (default 1): ");
                        String pr = in.nextLine();
                        if (!Objects.equals(pr, "1") && !Objects.equals(pr, "2"))
                            pr = "1";

                        long goodCount1 = 0;
                        long goodCount2 = 0;
                        long goodId = 0;

                        RestTemplate restTemplateSale = new RestTemplate();
                        String urlGood = "http://localhost:8080/bt/findSale/" + id;
                        ResponseEntity<String> responseEntitySale = restTemplateSale.getForEntity(urlGood, String.class);
                        Object objSale = new JSONParser().parse(responseEntitySale.getBody());
                        JSONObject joSale = (JSONObject) objSale;
                        long countGood = (long) joSale.get("goodCount");
                        JSONObject joGood = (JSONObject) joSale.get("good");
                        goodId = (long) joGood.get("id");

                        long idW1 = 0;
                        long idW2 = 0;

                        long sumCount = 0;
                        RestTemplate restTemplateWarehouse1 = new RestTemplate();
                        String urlWarehouse1 = "http://localhost:8080/bt/warehouse1ByGoodId/" + goodId;
                        ResponseEntity<String> responseEntityWarehouse1 = restTemplateWarehouse1.getForEntity(urlWarehouse1, String.class);

                        JSONArray array = (JSONArray) new JSONParser().parse(responseEntityWarehouse1.getBody());
                        if (array.size() > 0) {
                            for (int i = 0; i < array.size(); i++) {
                                Object obj = array.get(i);
                                JSONObject jo = (JSONObject) obj;
                                goodCount1 += (long) jo.get("goodCount");
                                sumCount += goodCount1;
                                idW1 = (long) jo.get("id");
                            }
                        }

                        RestTemplate restTemplateWarehouse2 = new RestTemplate();
                        String urlWarehouse2 = "http://localhost:8080/bt/warehouse2ByGoodId/" + goodId;
                        ResponseEntity<String> responseEntityWarehouse2 = restTemplateWarehouse2.getForEntity(urlWarehouse2, String.class);

                        array = (JSONArray) new JSONParser().parse(responseEntityWarehouse2.getBody());
                        if (array.size() > 0){
                            for (int i = 0; i < array.size(); i++) {
                                Object obj = array.get(i);
                                JSONObject jo = (JSONObject) obj;
                                goodCount2 += (long) jo.get("goodCount");
                                sumCount += goodCount2;
                                idW2 = (long) jo.get("id");
                            }
                        }

                        while (true){
                            if (countGood > sumCount) {
                                System.out.println("There is not enough product in warehouses");
                                break;
                            }
                            else{
                                if (pr.equals("1")){
                                    if (countGood > goodCount1){
                                        goodCount2 += goodCount1 - countGood;
                                        goodCount1 = 0;
                                    }
                                    else {
                                        goodCount1 -= countGood;
                                    }

                                    RestTemplate restTemplateW1 = new RestTemplate();
                                    String urlW1 = "http://localhost:8080/bt/updateWarehouse1/" + idW1 +"/" + goodCount1;
                                    HttpHeaders headersW1 = new HttpHeaders();
                                    headersW1.add("Authorization", "Bearer " + token);
                                    HttpEntity<String> requestW1 = new HttpEntity<>(headersW1);
                                    restTemplateW1.exchange(urlW1, HttpMethod.PUT, requestW1, String.class);

                                    RestTemplate restTemplateW2 = new RestTemplate();
                                    String urlW2 = "http://localhost:8080/bt/updateWarehouse2/" + idW2 +"/" + goodCount2;
                                    HttpHeaders headersW2 = new HttpHeaders();
                                    headersW2.add("Authorization", "Bearer " + token);
                                    HttpEntity<String> requestW2 = new HttpEntity<>(headersW2);
                                    restTemplateW2.exchange(urlW2, HttpMethod.PUT, requestW2, String.class);
                                }

                                if (pr.equals("2")){
                                    if (countGood > goodCount2){
                                        goodCount1 += goodCount2 - countGood;
                                        goodCount2 = 0;
                                    }
                                    else{
                                        goodCount2 -= countGood;
                                    }

                                    RestTemplate restTemplateW1 = new RestTemplate();
                                    String urlW1 = "http://localhost:8080/bt/updateWarehouse1/" + idW1 +"/" + goodCount1;
                                    HttpHeaders headersW1 = new HttpHeaders();
                                    headersW1.add("Authorization", "Bearer " + token);
                                    HttpEntity<String> requestW1 = new HttpEntity<>(headersW1);
                                    restTemplateW1.exchange(urlW1, HttpMethod.PUT, requestW1, String.class);

                                    RestTemplate restTemplateW2 = new RestTemplate();
                                    String urlW2 = "http://localhost:8080/bt/updateWarehouse2/" + idW2 +"/" + goodCount2;
                                    HttpHeaders headersW2 = new HttpHeaders();
                                    headersW2.add("Authorization", "Bearer " + token);
                                    HttpEntity<String> requestW2 = new HttpEntity<>(headersW2);
                                    restTemplateW2.exchange(urlW2, HttpMethod.PUT, requestW2, String.class);
                                }

                                RestTemplate restTemplate = new RestTemplate();
                                String url = "http://localhost:8080/bt/deleteSale/" + id;
                                HttpHeaders headers = new HttpHeaders();
                                headers.add("Authorization", "Bearer " + token);
                                HttpEntity<String> request = new HttpEntity<>(headers);
                                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                                if (responseEntity.getStatusCode().toString().equals("200 OK")){
                                    System.out.println("successful execute sale");
                                }
                                break;
                            }
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("warehouse1")){
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse1";
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("addWarehouse1")){
                    if (token != ""){
                        System.out.print("Input count: ");
                        String count = in.nextLine();
                        System.out.print("Input good ID: ");
                        String goodId = in.nextLine();

                        RestTemplate restTemplateGood = new RestTemplate();
                        String urlGood = "http://localhost:8080/bt/findGood/" + goodId;
                        ResponseEntity<String> responseEntityGood = restTemplateGood.getForEntity(urlGood, String.class);
                        String good = responseEntityGood.getBody();

                        RestTemplate restTemplateWarehouse1 = new RestTemplate();
                        String urlWarehouse1 = "http://localhost:8080/bt/warehouse1ByGoodId/" + goodId;
                        ResponseEntity<String> responseEntityWarehouse1 = restTemplateWarehouse1.getForEntity(urlWarehouse1, String.class);

                        long id = 0;
                        long lCount = Integer.parseInt(count);
                        JSONArray array = (JSONArray) new JSONParser().parse(responseEntityWarehouse1.getBody());
                        if (array.size() > 0) {
                            for (int i = 0; i < array.size(); i++){
                                Object obj = array.get(i);
                                JSONObject jo = (JSONObject) obj;
                                lCount += (long) jo.get("goodCount");
                                id = (long) jo.get("id");
                            }
                            RestTemplate restTemplate = new RestTemplate();
                            String url = "http://localhost:8080/bt/updateWarehouse1/" + id +"/" + lCount;
                            HttpHeaders headers = new HttpHeaders();
                            headers.add("Authorization", "Bearer " + token);
                            HttpEntity<String> request = new HttpEntity<>(headers);
                            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                            if (responseEntity.getStatusCode().toString().equals("200 OK")){
                                System.out.println("successful add warehouse");
                            }
                        }
                        else{
                            RestTemplate restTemplate = new RestTemplate();
                            String url = "http://localhost:8080/bt/addWarehouse1";
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer " + token);
                            HttpEntity<String> request = new HttpEntity<>("{\n" +
                                    "    \"good\": " + good + ",\n" +
                                    "    \"goodCount\": " + count + "\n" +
                                    "}", headers);

                            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request,  String.class);
                            if (responseEntity.getStatusCode().toString().equals("200 OK")){
                                System.out.println("successful add warehouse");
                            }
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("findWarehouse1")){
                    System.out.print("Input id: ");
                    String id = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/findWarehouse1/" + id;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    Object obj = new JSONParser().parse(responseEntity.getBody());
                    JSONObject jo = (JSONObject) obj;
                    if (jo.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        JSONObject joGood = (JSONObject) jo.get("good");
                        System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        System.out.format("+----+-------+-----+%n");
                    }
                }

                if (str.equals("warehouse1ByName")){
                    System.out.print("Input name: ");
                    String name = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse1ByName/" + name;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("warehouse1PriorityUnder")){
                    System.out.print("Input priority: ");
                    String priority = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse1PriorityUnder/" + priority;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("deleteWarehouse1")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/deleteWarehouse1/" + id;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful delete warehouse");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("updateWarehouse1")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        System.out.print("Input new count: ");
                        String count = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/updateWarehouse1/" + id +"/" + count;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful update good count");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("warehouse2")){
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse2";
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("addWarehouse2")){
                    if (token != ""){
                        System.out.print("Input count: ");
                        String count = in.nextLine();
                        System.out.print("Input good ID: ");
                        String goodId = in.nextLine();

                        RestTemplate restTemplateGood = new RestTemplate();
                        String urlGood = "http://localhost:8080/bt/findGood/" + goodId;
                        ResponseEntity<String> responseEntityGood = restTemplateGood.getForEntity(urlGood, String.class);
                        String good = responseEntityGood.getBody();


                        RestTemplate restTemplateWarehouse2 = new RestTemplate();
                        String urlWarehouse2 = "http://localhost:8080/bt/warehouse2ByGoodId/" + goodId;
                        ResponseEntity<String> responseEntityWarehouse2 = restTemplateWarehouse2.getForEntity(urlWarehouse2, String.class);

                        long id = 0;
                        long lCount = Integer.parseInt(count);
                        JSONArray array = (JSONArray) new JSONParser().parse(responseEntityWarehouse2.getBody());
                        if (array.size() > 0) {
                            for (int i = 0; i < array.size(); i++){
                                Object obj = array.get(i);
                                JSONObject jo = (JSONObject) obj;
                                lCount += (long) jo.get("goodCount");
                                id = (long) jo.get("id");
                            }
                            RestTemplate restTemplate = new RestTemplate();
                            String url = "http://localhost:8080/bt/updateWarehouse2/" + id +"/" + lCount;
                            HttpHeaders headers = new HttpHeaders();
                            headers.add("Authorization", "Bearer " + token);
                            HttpEntity<String> request = new HttpEntity<>(headers);
                            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                            if (responseEntity.getStatusCode().toString().equals("200 OK")){
                                System.out.println("successful add warehouse");
                            }
                        }
                        else{
                            RestTemplate restTemplate = new RestTemplate();
                            String url = "http://localhost:8080/bt/addWarehouse2";
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer " + token);
                            HttpEntity<String> request = new HttpEntity<>("{\n" +
                                    "    \"good\": " + good + ",\n" +
                                    "    \"goodCount\": " + count + "\n" +
                                    "}", headers);

                            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request,  String.class);
                            if (responseEntity.getStatusCode().toString().equals("200 OK")){
                                System.out.println("successful add warehouse");
                            }
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("findWarehouse2")){
                    System.out.print("Input id: ");
                    String id = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/findWarehouse2/" + id;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    Object obj = new JSONParser().parse(responseEntity.getBody());
                    JSONObject jo = (JSONObject) obj;
                    if (jo.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        JSONObject joGood = (JSONObject) jo.get("good");
                        System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        System.out.format("+----+-------+-----+%n");
                    }
                }

                if (str.equals("warehouse2ByName")){
                    System.out.print("Input name: ");
                    String name = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse2ByName/" + name;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("warehouse2Priority")){
                    System.out.print("Input priority: ");
                    String priority = in.nextLine();
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/bt/warehouse2Priority/" + priority;
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    JSONArray array = (JSONArray) new JSONParser().parse(responseEntity.getBody());
                    if (array.size() > 0) {
                        System.out.format("+----+-------+-----+%n");
                        System.out.format("|id  |good id|count|%n");
                        System.out.format("+----+-------+-----+%n");
                        for (int i = 0; i < array.size(); i++){
                            Object obj = array.get(i);
                            JSONObject jo = (JSONObject) obj;
                            JSONObject joGood = (JSONObject) jo.get("good");
                            System.out.format("|%4d|%7d|%5d|%n", jo.get("id"), joGood.get("id"), jo.get("goodCount"));
                        }
                        System.out.format("+----+-------+-----+%n");
                    }
                    else{
                        System.out.println("Table is empty");
                    }
                }

                if (str.equals("updateWarehouse2")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        System.out.print("Input new count: ");
                        String count = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/updateWarehouse2/" + id +"/" + count;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful update good count");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

                if (str.equals("deleteWarehouse2")){
                    if (token != ""){
                        System.out.print("Input id: ");
                        String id = in.nextLine();
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:8080/bt/deleteWarehouse2/" + id;
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Authorization", "Bearer " + token);
                        HttpEntity<String> request = new HttpEntity<>(headers);
                        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                        if (responseEntity.getStatusCode().toString().equals("200 OK")){
                            System.out.println("successful delete warehouse");
                        }
                    }
                    else
                        System.out.println("Please log in");
                }

            }
            catch (HttpClientErrorException e) {
                String message = e.getResponseBodyAsString();
            }
        }

    }
}
