package prince.pojo;

import java.util.List;

public class AnimationElement {
    private String name;

    private List<Sequence> sequences;

    public AnimationElement(String name, List<Sequence> sequences) {
        this.name = name;
        this.sequences = sequences;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }
}
