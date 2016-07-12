package com.oneapm.research.correlation.web;

import com.oneapm.research.correlation.CorrelationTest;
import com.oneapm.research.correlation.web.model.CorrelationResultModel;
import jodd.json.JsonSerializer;
import spark.Request;
import spark.Route;

import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

/**
 * Created by ruan on 16-7-12.
 */
public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        staticFileLocation("/public");
        port(9091);
        get("/", index());
        get("/correlationresult", getCorrelationResult());
    }

    private static Route getCorrelationResult() {
        // TODO Auto-generated method stub
        return new Route() {

            @Override
            public Object handle(Request request, spark.Response response) throws Exception {
                // TODO Auto-generated method stub
                Config.fetchFromRequest(request);
                // Correlation Result
                CorrelationResultModel result = CorrelationTest.correlationTest();

                JsonSerializer serializer = new JsonSerializer();
                serializer.deep(true);
                System.out.println(serializer.serialize(result));
                return serializer.serialize(result);

            }

        };
    }

    public static Route index() {
        return new Route() {

            @Override
            public Object handle(Request request, spark.Response response) throws Exception {
                // TODO Auto-generated method stub
                response.redirect("/index.html");
                return null;
            }

        };
    }

}
