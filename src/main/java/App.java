import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "layout.hbs";

//        BasicConfigurator.configure();

            ProcessBuilder process = new ProcessBuilder();
            Integer port;
            if (process.environment().get("PORT") != null) {
                port = Integer.parseInt(process.environment().get("PORT"));
            } else {
            port = 4567;
        }

        port(port);


        get("/", (req, res) -> {
            System.out.println(Squad.all());
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.all());
            model.put("template", "categories.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.all());
            return new ModelAndView(model, "categories.hbs");
        },new HandlebarsTemplateEngine());

        post("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String size = req.queryParams("size");
            String cause = req.queryParams("cause");
            Squad squad = new Squad(Integer.parseInt(size),name,cause);
            return new ModelAndView(model, "squad-success.hbs");
        },new HandlebarsTemplateEngine());

        get("squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "categories_form.hbs");
            return new ModelAndView(model, "categories_form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
            model.put("template", "squad.hbs");
            return new ModelAndView(model, "squad.hbs");
        },new HandlebarsTemplateEngine());

        get("/squads/:id/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
            model.put("template", "squad-heroes-form.hbs");
            return new ModelAndView(model, "squad-heroes-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/heroes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.queryParams("squadId")));
            String name = req.queryParams("name");
            String age = req.queryParams("age");
            String special_power = req.queryParams("special_power");
            String weakness = req.queryParams("special_power");


            if (Hero.findHeroByName(name.trim()))
            {

                model.put("template", "heroes-fail.hbs");
                model.put("squad",squad);
            }
            else
            {
                Hero hero = new Hero(name,Integer.parseInt(age),special_power,weakness);
                squad.addHero(hero);
                model.put("squad",squad);
            }
            return new ModelAndView(model, "heroes-success.hbs");
        },new HandlebarsTemplateEngine());

        // 500
        internalServerError((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "500.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model,"500.hbs")
            );
        });

        // 400
        notFound((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "400.hbs");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "400.hbs")
            );
        });
    }
}