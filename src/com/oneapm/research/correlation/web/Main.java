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
        System.out.println("1");
        staticFileLocation("/public");
        System.out.println("2");
        port(9090);
        System.out.println("3");
        get("/", index());
        System.out.println("4");
        get("/correlationresult", getCorrelationResult());
        System.out.println("5");
    }

    private static Route getCorrelationResult() {
        return new Route() {

            @Override
            public Object handle(Request request, spark.Response response) throws Exception {
                System.out.println("correlation");
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
