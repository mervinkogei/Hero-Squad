import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
//        String layout = "templates/layout.vtl";

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
            model.put("template", "templates/categories.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.all());
            model.put("template", "templates/categories.vtl");
            return new HandlebarsTemplateEngine());.render(
                    new ModelAndView(model, layout)
            );
        });

        post("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String size = req.queryParams("size");
            String cause = req.queryParams("cause");
            Squad squad = new Squad(Integer.parseInt(size),name,cause);
            model.put("template", "templates/squad-success.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/categories_form.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
            model.put("template", "templates/squad.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/squads/:id/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad squad = Squad.find(Integer.parseInt(req.params(":id")));
            model.put("squad", squad);
            model.put("template", "templates/squad-heroes-form.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
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

                model.put("template", "templates/heroes-fail.vtl");
                model.put("squad",squad);
            }
            else
            {
                Hero hero = new Hero(name,Integer.parseInt(age),special_power,weakness);
                squad.addHero(hero);
                model.put("squad",squad);
                model.put("template", "templates/heroes-success.vtl");
            }
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        // 500
        internalServerError((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //res.type("application/json");
            model.put("template", "templates/500.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        // 400
        notFound((req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //res.type("application/json");
            model.put("template", "templates/400.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });
    }
}
