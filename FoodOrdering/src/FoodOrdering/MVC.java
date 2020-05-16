package FoodOrdering;

public class MVC {
    View view = new View();
    Model model = new Model();
    Controller controller = new Controller(model,view);

}
