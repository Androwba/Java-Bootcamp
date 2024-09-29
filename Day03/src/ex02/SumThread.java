class SumThread implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private int segmentSum;

    public SumThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.segmentSum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            segmentSum += array[i];
        }
    }

    public int getSegmentSum() {
        return segmentSum;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
