package OPEarth;

import java.util.Iterator;

import org.json.*;

public class IterableJSONArray implements Iterable<JSONObject> {

	private JSONArray original;
	
    public IterableJSONArray(JSONArray original) {
        this.original = original;
    }

    public Iterator<JSONObject> iterator() {
        return new InnerIterator();
    }
	
    private class InnerIterator implements Iterator<JSONObject> {
        private int index;
        public boolean hasNext() {
            return index < original.length();
        }

        public JSONObject next() {
            JSONObject temp = null;
			try {
				temp = original.getJSONObject(index);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            index++;
            return temp;
        }

        public void remove() {
        	//
        }
    }
}
