package cz.colormemory.json;

import java.io.IOException;
import java.io.Writer;

/*******************************************************************************
 * JSONConstructor provides a quick and convenient way of producing JSON text.
 * The texts produced strictly conform to JSON syntax rules. No whitespace is
 added, so the results are ready for transmission or storage. Each instance of
 JSONConstructor can produce one JSON text.
 <p>
 A JSONConstructor instance provides a <code>value</code> method for appending
 * values to the
 * text, and a <code>key</code>
 * method for adding keys before values in objects. There are <code>array</code>
 * and <code>endArray</code> methods that make and bound array values, and
 * <code>object</code> and <code>endObject</code> methods which make and bound
 object values. All of these methods return the JSONConstructor instance,
 permitting a cascade style. For example, <pre>
 new JSONConstructor(myWriter)
     .object()
         .key("JSON")
         .value("Hello, World!")
     .endObject();</pre> which writes <pre>
 * {"JSON":"Hello, World!"}</pre>
 * <p>
 * The first method called must be <code>array</code> or <code>object</code>.
 There are no methods for adding commas or colons. JSONConstructor adds them for
 you. Objects and arrays can be nested up to 20 levels deep.
 <p>
 * This can sometimes be easier than using a JSONObject to build a string.
 *
 * @author JSON.org & André Heller
 * @version 3
 */
 public class JSONConstructor
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Maximal depth of nest */
    private static final int maxdepth = 20;

//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** The comma flag determines if a comma should be output before the next
     * value.*/
    private boolean comma;

    /** The current mode. Values:
     * 'a' (array),
     * 'd' (done),
     * 'i' (initial),
     * 'k' (key),
     * 'o' (object).
     */
    private char mode;

    /** The object/array stack. */
    private char stack[];

    /** The stack top index. A value of 0 indicates that the stack is empty. */
    private int top;

    /** The StringBuilder that will receive the output. */
    private StringBuilder sb = new StringBuilder();

//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Make a fresh JSONWriter. It can be used to build one JSON text.
     */
    public JSONConstructor(/*Writer w*/) {
        this.comma = false;
        this.mode = 'i';
        this.stack = new char[maxdepth];
        this.top = 0;
        //this.writer = w;
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Begin appending a new array. All values until the balancing
     * <code>endArray</code> will be appended to this array. The
     * <code>endArray</code> method must be called to mark the array's end.
     *
     * @return this
     * @throws JSONException If the nesting is too deep, or if the object is
     * started in the wrong place (for example as a key or after the end of the
     * outermost array or object).
     */
    public JSONConstructor array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push('a');
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }


    /***************************************************************************
     * End an array. This method most be called to balance calls to
     * <code>array</code>.
     *
     * @return this
     * @throws JSONException If incorrectly nested.
     */
    public JSONConstructor endArray() throws JSONException {
        return this.end('a', ']');
    }


    /***************************************************************************
     * Begin appending a new object. All keys and values until the balancing
     * <code>endObject</code> will be appended to this object. The
     * <code>endObject</code> method must be called to mark the object's end.
     *
     * @return this
     * @throws JSONException If the nesting is too deep, or if the object is
     * started in the wrong place (for example as a key or after the end of the
     * outermost array or object).
     */
    public JSONConstructor object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push('k');
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");

    }


    /***************************************************************************
     * End an object. This method most be called to balance calls to
     * <code>object</code>.
     *
     * @return this
     * @throws JSONException If incorrectly nested.
     */
    public JSONConstructor endObject() throws JSONException {
        return this.end('k', '}');
    }


    /***************************************************************************
     * Append a key. The key will be associated with the next value. In an
     * object, every value must be preceded by a key.
     *
     * @param s A key string.
     * @return this
     * @throws JSONException If the key is out of place. For example, keys
     *  do not belong in arrays or if the key is null.
     */
    public JSONConstructor key(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                if (this.comma) {
                    //this.writer.write(',');
                    this.sb.append(',');
                }
                //this.writer.write(JSONObject.quote(s));
                //this.writer.write(':');
                this.sb.append(JSONObject.quote(s)).append(':');
                this.comma = false;
                this.mode = 'o';
                return this;
            } catch (Exception e) {
                throw new JSONException(e);
            }
        }
        throw new JSONException("Misplaced key.");
    }


    /***************************************************************************
     * Append either the value <code>true</code> or the value
     * <code>false</code>.
     *
     * @param b A boolean.
     * @return this
     * @throws JSONException
     */
    public JSONConstructor value(boolean b) throws JSONException {
        return this.append(b ? "true" : "false");
    }


    /***************************************************************************
     * Append a long value.
     *
     * @param l A long.
     * @return this
     * @throws JSONException
     */
    public JSONConstructor value(long l) throws JSONException {
        return this.append(Long.toString(l));
    }


    /***************************************************************************
     * Append an object value.
     *
     * @param o The object to append. It can be null, or a Boolean, Number,
     *   String, JSONObject, or JSONArray, or an object with a toJSONString()
     *   method.
     * @return this
     * @throws JSONException If the value is out of sequence.
     */
    public JSONConstructor value(Object o) throws JSONException {
        return this.append(JSONObject.valueToString(o));
    }


    /***************************************************************************
     * Return the JSON text. This method is used to obtain the product of the
     * JSONStringer instance. It will return <code>null</code> if there was a
     * problem in the construction of the JSON text (such as the calls to
     * <code>array</code> were not properly balanced with calls to
     * <code>endArray</code>).
     *
     * @return The JSON text.
     */
    @Override
    public String toString() {
        return this.mode == 'd' ? this.sb.toString() : null;
    }

//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Append a value.
     *
     * @param s A string value.
     * @return this
     * @throws JSONException If the value is out of sequence.
     */
    private JSONConstructor append(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    //this.writer.write(',');
                    this.sb.append(',');
                }
                //this.writer.write(s);
                this.sb.append(s);
            } catch (Exception e) {
                throw new JSONException(e);
            }
            if (this.mode == 'o') {
                this.mode = 'k';
            }
            this.comma = true;
            return this;
        }
        throw new JSONException("Value out of sequence.");
    }


    /***************************************************************************
     * End something.
     *
     * @param m Mode
     * @param c Closing character
     * @return this
     * @throws JSONException If unbalanced.
     */
    private JSONConstructor end(char m, char c) throws JSONException {
        if (this.mode != m) {
            throw new JSONException(m == 'o' ? "Misplaced endObject." :
                "Misplaced endArray.");
        }
        this.pop(m);
        try {
            //this.writer.write(c);
            this.sb.append(c);
        } catch (Exception e) {
            throw new JSONException(e);
        }
        this.comma = true;
        return this;
    }


    /***************************************************************************
     * Pop an array or object scope.
     *
     * @param c The scope to close.
     * @throws JSONException If nesting is wrong.
     */
    private void pop(char c) throws JSONException {
        if (this.top <= 0 || this.stack[this.top - 1] != c) {
            throw new JSONException("Nesting error.");
        }
        this.top -= 1;
        this.mode = this.top == 0 ? 'd' : this.stack[this.top - 1];
    }


    /***************************************************************************
     * Push an array or object scope.
     *
     * @param c The scope to open.
     * @throws JSONException If nesting is too deep.
     */
    private void push(char c) throws JSONException {
        if (this.top >= maxdepth) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = c;
        this.mode = c;
        this.top += 1;
    }


//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /*************************************************************************
//     * Testing method.
//     */
//    public static void test()
//    {
//        JSONConstructor inst = new JSONConstructor();
//    }
//    /** @param args Command line arguments - not used. */
//    public static void main(String[] args)  {  test();  }
}
