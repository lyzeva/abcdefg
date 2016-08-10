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
        staticFileLocation("/public");
        port(9090);
        get("/", index());
        System.out.println(Double.toString(Double.MIN_VALUE));
        get("/correlationresult", getCorrelationResult());
    }

    private static Route getCorrelationResult() {
        return new Route() {

            @Override
            public Object handle(Request request, spark.Response response) throws Exception {
                Config.fetchFromRequest(request);
                // Correlation Result
                CorrelationResultModel result = CorrelationTest.correlationTest(Config.starttime, Config.endtime);

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
                response.redirect("/index.html");
                System.out.println("Index");
                return null;
            }

        };
    }

}
