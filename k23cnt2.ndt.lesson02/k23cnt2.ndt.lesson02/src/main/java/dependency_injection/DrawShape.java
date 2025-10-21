package dependency_injection;

public class DrawShape {
    private Shape shape;

    // Constructor Injection
    public DrawShape(Shape shape) {
        this.shape = shape;
    }

    public void Draw() {
        shape.draw();
    }

    public static void main(String[] args) {
        // Tiêm phụ thuộc: CircleShape
        DrawShape drawShape = new DrawShape(new CircleShape());
        drawShape.Draw();

        // Tiêm phụ thuộc: RectangleShape
        drawShape = new DrawShape(new RectangleShape());
        drawShape.Draw();
    }
}

