package smo;

import java.util.ArrayList;

public class Buffer {
    private static int lastRequestNum = 0; //Add to buffer
    private int lastDelNum = 0;
    private final int bufferSize;
    private int requestNum;
    private final ArrayList<Request> requestQueue;
    private final int[] rejectedRequests;
    private Request lastRejectedRequest;

    public Buffer(int bufferSize, int sourceNum) {
        this.bufferSize = bufferSize;
        this.requestQueue = new ArrayList<>(bufferSize);
        this.requestNum = 0;
        this.rejectedRequests = new int[sourceNum];
    }

    public boolean isEmpty() {
        return requestNum == 0;
    }

    public boolean hasFreeSpaces() {
        return requestNum < bufferSize;
    }

    public void putRequest(Request request) {
        if (lastDelNum == 0 || requestQueue.size() == 0) {
            requestQueue.add(request);
        }
        else {
            requestQueue.add(lastDelNum, request);
            lastDelNum = 0;
        }



        //requestQueue.add(request);
        ++requestNum;
    }

    public void replaceNewestRequest(Request request) {
        int newestRequestIndex = 0;
        double time = requestQueue.get(0).getGenerationTime();
        for (Request req : requestQueue) {
            if (req.getGenerationTime() > time) {
                newestRequestIndex = requestQueue.indexOf(req);
                time = req.getGenerationTime();
            }
        }

        lastRejectedRequest = requestQueue.get(newestRequestIndex);
        ++rejectedRequests[lastRejectedRequest.getSourceNumber()];

        requestQueue.set(newestRequestIndex,request);
    }

    Request getLastRequest() {
        Request lastRequest; //= requestQueue.get(0);
        if (lastRequestNum >= requestQueue.size()) {
            lastRequestNum = 0;
            lastRequest = requestQueue.get(lastRequestNum);
        }
        else {
            lastRequest = requestQueue.get(lastRequestNum);
        }
        requestQueue.remove(lastRequestNum);
        lastDelNum = lastRequestNum;
        ++lastRequestNum;
        /*if (lastRequestNum >= requestQueue.size()) {
            lastRequestNum = 0;
        }*/

        --requestNum;
        return lastRequest;
    }

    public int getLastRequestNum() {
        return lastRequestNum;
    }

    public int[] getRejectedRequests() {
        return rejectedRequests;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public ArrayList<Request> getRequestQueue() {
        return requestQueue;
    }

    public Request getLastRejectedRequest() {
        return lastRejectedRequest;
    }
}
