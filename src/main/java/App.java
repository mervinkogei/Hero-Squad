import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;
//import static spark.Spark.get;
//import static spark.Spark.post;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.hbs";

        ProcessBuilder process = new ProcessBuilder();
        Integer port;


        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("squads", Squad.all());
            return new ModelAndView(model, "categories.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.all());
//            model.put("template", "templates/categories.vtl");
            return new ModelAndView(model, "categories.hbs");
//                    new ModelAndView(model, layout)
        }, new HandlebarsTemplateEngine());


        post("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String size = req.queryParams("size");
            String cause = req.queryParams("cause");
            Squad squad = new Squad(Integer.parseInt(size),name,cause);
            model.put("template", "templates/squad-success.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "squad-success.hbs")
            );
        });

        get("squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/categories_form.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "categories-form.hbs")
            );
        });

        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
//            model.put("template", "templates/squad.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "squad.hbs")
            );
        });

        get("/squads/:id/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
            model.put("template", "templates/squad-heroes-form.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "squad-heroes-form.hbs")
            );
        });

        post("/heroes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.queryParams("squadId")));
            String name = req.queryParams("name");
            String age = req.queryParams("age");
            String special_power = req.queryParams("special_power");
            String weakness = req.queryParams("special_power");


            if (Hero.findHeroByName(name.trim()))
            {

                model.put("template", "templates/heroes-fail.hbs");
                model.put("squad",squad);
            }
            else
            {
                Hero hero = new Hero(name,Integer.parseInt(age),special_power,weakness);
                squad.addHero(hero);
                model.put("squad",squad);
                model.put("template", "templates/heroes-success.hbs");
            }
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "heroes-success.hbs")
            );
        });

        // 500
        internalServerError((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //res.type("application/json");
            model.put("template", "templates/500.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "500.hbs")
            );
        });

        // 400
        notFound((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //res.type("application/json");
            model.put("template", "templates/400.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "400.hbs")
            );
        });
    }
}
