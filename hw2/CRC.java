//Geunuk Na, geunuk.na@stonybrook.edu, 111000447;

package hw214_2;

//Cyclic Redundancy Check
@SuppressWarnings("unchecked")
public class CRC {
    static final IntMod I = new IntMod(1, 2);
    static final IntMod O = new IntMod(0, 2);
    
    
    public static Poly<IntMod> sendMessage(Poly<IntMod> msg, Poly<IntMod> gen) {
        checkPoly(gen);
        checkPoly(msg);
        
        int l = ((Field[])gen.getCoef()).length;
        IntMod[] n = new IntMod[l];
        n[l-1] = I;
        for(int i=0; i<l-1;i++) {
        	n[i]=O;
        }
 
        Poly<IntMod> shift = new Poly<IntMod>(n);
        //TODO: compute x^r, where gen = x^r + ... + 1
        Poly<IntMod> shiftMsg = (Poly<IntMod>)shift.mul(msg);
        //TODO: compute shift * msg
        Poly<IntMod> checksum = (Poly<IntMod>)shiftMsg.mod(gen);
        //TODO: compute shiftMsg mod gen
        Poly<IntMod> txMsg = (Poly<IntMod>)shiftMsg.add(checksum.addInverse());   
        //TODO: compute shftMsg - txMsg
        
        printPoly("msg:      ", msg);
        printPoly("gen:      ", gen);
        printPoly("shift:    ", shift);
        printPoly("shiftMsg: ", shiftMsg);
        printPoly("checksum: ", checksum);
        printPoly("txMsg:    ", txMsg);
        return txMsg;
    }

    public static boolean checkMessage(Poly<IntMod> msg, Poly<IntMod> gen) {
        checkPoly(gen);
        checkPoly(msg);

        Ring rem = msg.mod(gen);//TODO: cmopute msg mod gen
        printPoly("rem:      ", (Poly<IntMod>)rem);

        return Comp.eq(rem, rem.addIdentity());
    }

    public static Poly<IntMod> receiveMessage(Poly<IntMod> msg, Poly<IntMod> gen) {
        checkPoly(gen);
        checkPoly(msg);
        
        int l = ((Field[])gen.getCoef()).length;
        IntMod[] n = new IntMod[l];
        n[l-1]= I;
        for(int i=0; i<l-1;i++) {
        	n[i]=O;
        }
        
        Poly<IntMod> shift = new Poly<IntMod>(n);
        //TODO: compute x^r, where gen = x^r + ... + 1
        Poly<IntMod> rxMsg = (Poly<IntMod>)msg.quo(shift);
        //TODO: compute msg quo shift
        
        printPoly("shift:    ", shift);
        printPoly("rxMsg:    ", rxMsg);
        return rxMsg;
    }
    
    protected static Poly<IntMod> shiftPoly(Poly<IntMod> gen) {
        int shiftLen = ((Field[])gen.getCoef()).length;

        IntMod[] shiftCoef = new IntMod[shiftLen];
        shiftCoef[shiftLen-1] = I;
        for(int i=0; i<shiftLen-1; i++) {
        	shiftCoef[i]=O;
        }
        return new Poly<IntMod>(shiftCoef);
        //TODO: compute x^r, where gen = x^r + ... + 1
    }
    
    protected static void checkPoly(Poly<IntMod> poly) {
        for(Field coef : poly.getCoef())
            if(((IntMod)coef).getMod() != 2)
                throw new IllegalArgumentException("Not a modulo 2 polynomial");
    }

    protected static void printPoly(String tag, Poly<IntMod> poly) {
        System.out.print(tag);
        for(Field coef : poly.getCoef())
            System.out.print(((IntMod)coef).getInt());
        System.out.println();
    }
    
    public static void testCRC() {
        /* expected output
                msg:      1101101011
                gen:      11001
                shift:    00001
                shiftMsg: 00001101101011
                checksum: 0111
                txMsg:    01111101101011
                rem:      0
                shift:    00001
                rxMsg:    1101101011
                testCRC success!
         */
        Poly<IntMod> message   = new Poly<IntMod>(new IntMod[] {I, I, O, I, I, O, I, O, I, I});
        Poly<IntMod> generator = new Poly<IntMod>(new IntMod[] {I, I, O, O, I});
        Poly<IntMod> answer    = new Poly<IntMod>(new IntMod[] {O, I, I, I, I, I, O, I, I, O, I, O, I, I});
        
        Poly<IntMod> txMsg = sendMessage(message, generator);
        if(!Comp.eq(answer, txMsg))
            throw new RuntimeException("Error: unexpected");
        
        if(!checkMessage(txMsg, generator))
            throw new RuntimeException("Error: unexpected");

        Poly<IntMod> rxMsg = receiveMessage(txMsg, generator);
        if(!Comp.eq(message, rxMsg))
            throw new RuntimeException("Error: unexpected");
        
        System.out.println("testCRC success!");
    }
    
    public static void main(String[] args) {
        testCRC();
    }
}

