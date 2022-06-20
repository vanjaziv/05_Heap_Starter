package UE05_TaskHeap;

public class TaskHeap
{
    private Task[] tasks;  // Heap wird in einem Array verwaltet
    private int count = 0;

    public int getCount()
    {
        return count;
    }

    public TaskHeap(int size)
    {
        tasks = new Task[size];
    }

    private int parent(int pos)
    {
        // rundet automatisch ab, weil Integer-Division
        return (pos-1) / 2;
    }

    private int left(int pos)
    {
        return pos * 2 + 1;
    }

    private int right(int pos)
    {
        return (left(pos)) + 1;
    }

    private boolean exists(int pos)
    {
        return (pos >= 0 && pos < count);
    }

    private int prio(int pos)
    {
        return tasks[pos].getPriority();
    }

    // Hilfsfunktion, die bei swim() und sink() verwendet wird
    private void exchange(int pos1, int pos2)
    {
        Task temp;

        temp = tasks[pos1];
        tasks[pos1] = tasks[pos2];
        tasks[pos2] = temp;
    }

    private void swim(int pos)
    {
        int currentPos, parentPos;

        // Start bei der übergebenen Position im Array
        currentPos = pos;

        while (exists(currentPos))
        {
            // Position des Parents finden
            parentPos = parent(currentPos);

            // Wenn Prioritätswert vom Parent höher als vom Current, ...
            if (exists(parentPos) && prio(parentPos) > prio(currentPos))
            {
                // ... dann tauschen
                exchange(currentPos, parentPos);
                // ... und beim nächsten Schleifendurchlauf bei Position vom früheren Parent wiederholt prüfen
                currentPos = parentPos;
            } else
            {
                break;
            }
        }
    }

    private void sink(int pos)
    {
        int currentPos, minPos;

        // Start bei der übergebenen Position
        currentPos = pos;

        while (exists(currentPos))
        {
            // Wenns bei aktueller Position keine Kinder gibt, ...
            if (!hasChildren(currentPos))
            {
                // ... dann abbrechen
                break;
            }

            // Position des Kindes mit der kleineren Priorität finden ("wichtigeres" Kind)
            minPos = minChild(currentPos);

            // Wenn Kind kleineren Prioritätswert hat (Kind also wichtiger ist), als aktuelle Position, dann ...
            if (prio(minPos) < prio(currentPos))
            {
                // ... dann tauschen
                exchange(minPos, currentPos);
                // ... und beim nächsten Schleifendurchlauf bei Position vom früheren Kind wiederholt prüfen
                currentPos = minPos;
            } else
            {
                break;
            }
        }
    }

    // Hilfsfunktion, um Kind mit kleinerem Prioritätswert zu finden
    private int minChild(int pos)
    {
        int min, l, r;
        l = left(pos);
        r = right(pos);

        min = l;
        if (exists(r) && prio(r) < prio(l))
        {
            min = r;
        }
        return min;
    }

    // Hilfsfunktion, um zu prüfen, ob ein Knoten überhaupt Kinder hat
    private boolean hasChildren(int pos)
    {
        int l;
        // man muss nur "links" prüfen, da ein Heap immer von links nach rechts aufgebaut wird
        l = left(pos);

        return exists(l);
    }

    // Hilfsfunktion, um zu prüfen, ob Array voll ist
    public boolean isFull()
    {
        return (count >= tasks.length);
    }

    public boolean insert(Task t)
    {
        // Die Anweisungen a) bis f) sind durcheinander gekommen.
        // TODO: In welcher Reihenfolge müssen die Anweisungen angeordnet sein, damit insert(...) korrekt funktioniert?
        // d)
        if (isFull())
        {
            System.err.println("Heap ist voll!");
            return false;
        }
        // a)
        int newPos = count;
        // e)
        tasks[newPos] = t;
        // b)
        count++;
        // c) Tipp: swim() benötigt einen korrekt gesetzten count-Wert
        swim(newPos);

        // Array-Index beginnt bei 0! Darum kann count unverändert verwendet werden.
        // Beispiel: Im Array sind 2 Elemente (0:A, 1:B, 2:Null). Count ist 2. Bei Index 2 nächstes Element einfügen.

        // f)
        return true;
    }


    public Task remove()
    {
        Task resultTask;

        // Diese Methode liefert das erste Element zurück und löscht es aus dem Heap
        // Die Anweisungen a) bis f) sind durcheinander gekommen.
        // TODO: In welcher Reihenfolge müssen die Anweisungen angeordnet sein, damit remove() korrekt funktioniert?

        // a)
        if (count == 0)
        {
            System.err.println("Heap ist leer!");
            return null;
        }
        // e)
        resultTask = tasks[0];
        // c)
        tasks[0] = tasks[count-1];
        // f)
        tasks[count-1] = null;
        // b)
        count--;
        // d) Tipp: sink() benötigt einen korrekt gesetzten count-Wert
        sink(0);
        // g)
        return resultTask;
    }

    // Ausgabe des Heaps auf der Konsole
    public void printHeap()
    {
        if (!exists(0))
        {
            return;
        }
        printHeap(0, "");
    }

    private void printHeap(int node, String indent)
    {
        if (!exists(node))
        {
            return;
        }
        Task t = tasks[node];
        System.out.println(indent + t.toString());
        printHeap(left(node), indent + "  ");
        printHeap(right(node), indent + "  ");
    }
}
