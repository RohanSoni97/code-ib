import java.io.*;
import java.util.*;
class A
{
    private static HashMap<Integer,Integer> map;
    private static int idx;
    private static BIT FT;
    static class BIT //Binary-Indexed Tree
    {
        int size;
        int[] table;

        BIT(int size)
        {
            table=new int[size+1];
            this.size=size+1;
        }
        void update(int i, int delta) //update function
        {
            i++;
            while(i<size)
            {
                table[i]+=delta;
                i+=Integer.lowestOneBit(i); //i+=i&-i
            }
        }
        int sum(int i) //query function
        {
            int s=0; i++;
            while (i>0)
            {
                s+=table[i];
                i-=Integer.lowestOneBit(i);//i-=i&-i
            }
            return s;
        }
        int rangeSum(int i, int j){return sum(j)-sum(i-1);} //range query
    }

    // Used this because Arrays.sort() uses quicksort in Java
    //which may have O(N^2) complexity for certain types of arrays.
    private static void shuffleArray(int[] arr)
    {
        int n = arr.length;
        Random rnd = new Random();
        for(int i=0; i<n; ++i)
        {
            int tmp = arr[i];
            int randomPos = i + rnd.nextInt(n-i);
            arr[i] = arr[randomPos];
            arr[randomPos] = tmp;
        }
    }

    //counts the number of subarrays which have an inversion count
    //less than or equal to mid
    private static long count(int[] a, int N, long mid)
    {
        Arrays.fill(FT.table,0);

        long cur=0, cnt=0;
        int l=0,r=-1,pos,next;

        //l and r and the left and right borders of the current window
        while (l<N&&r<N)
        {
            if(r<N-1)
            {
                pos=map.get(a[r+1]); //compressed position

                //how much will extending our window add to the
                //inversion count
                next=FT.rangeSum(pos+1,idx);

                //while the inversion count remains less than or equal to mid
                //keep extending the window
                while (cur+next<=mid&&r<N-1)
                {
                    r++;
                    cur+=next;
                    FT.update(pos,1);

                    if(r<N-1)
                    {
                        pos = map.get(a[r+1]);
                        next = FT.rangeSum(pos + 1, idx);
                    }
                }
            }

            //number of subarrays starting at l which satisfy the condition
            cnt+=r-l+1;

            //remove the contribution and effect of a[l] from
            //current window count before moving on to the next l.
            pos=map.get(a[l++]);
            cur-=FT.sum(pos-1);
            FT.update(pos,-1);
        }
        return cnt;
    }
    public static void main(String[] args) throws IOException
    {
        Reader reader=new Reader();

        int i,N;

        int T=reader.nextInt();
        StringBuilder sb=new StringBuilder();

        while(T-->0)
        {
            N=reader.nextInt();

            int[] a=new int[N], sorted=new int[N];
            for(i=0;i<N;i++) a[i]=sorted[i]=reader.nextInt();

            shuffleArray(sorted);
            Arrays.sort(sorted);

            map=new HashMap<>(); idx=1;
            map.put(sorted[0],0);
            for(i=1;i<N;i++) //coordinate compression
            {
                if(sorted[i]==sorted[i-1]) continue;
                map.put(sorted[i],idx++);
            }

            FT=new BIT(idx+5);
            long l=0,r=(long)N*(N-1)/2,mid,ans=r;
            final long medianPos=(1+(long)N*(N+1)/2)/2;

            while (l<=r)
            {
                mid=(l+r)/2;

                //find the smallest one which satisfies the condition
                if(count(a,N,mid)>=medianPos)
                {
                    ans=mid;
                    r=mid-1;
                }
                else l=mid+1;
            }
            sb.append(ans).append("\n");
        }
        System.out.println(sb);
    }
    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
        public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
        }public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
    }public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);
    }public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;
    }private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;
    }private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];
    }public void close() throws IOException{if(din==null) return;din.close();}
    }
}