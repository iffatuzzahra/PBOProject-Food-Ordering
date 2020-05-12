package FoodOrdering;

public class MVC {
    //View vieww;
    //JFrame currentFrame = new JFrame();
    View view = new View();
    Model model = new Model();
    Controller controller = new Controller(model,view);

}