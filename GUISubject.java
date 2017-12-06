public interface GUISubject
{
    public void initializeGUISubject();
    public void attach(InstructorObserver io);
    public void detach(InstructorObserver io);
    public void notifyObservers(String update);
}