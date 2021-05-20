//Wachirayana Wanprasert 6088082
//Chavanont Sakolpongpairoj 6088157
//Panaya Sirilertworakul 6088164

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class BasicIndex implements BaseIndex {

	@Override
	public PostingList readPosting(FileChannel fc) {
		/*
		 * TODO: Your code here
		 *       Read and return the postings list from the given file.
		 */
		
		PostingList posting =null;
		ByteBuffer byteb = ByteBuffer.allocate(4);
		try {

			if(fc.position()>=fc.size()) {
				return null;
			}
			else {
				int cB,f;
				cB=fc.read(byteb);
				byteb.flip();
				posting = new PostingList(byteb.getInt());
				byteb.clear();

				cB=fc.read(byteb);
				byteb.flip();
				f=byteb.getInt();
				byteb.clear();
				for(int i=0;i<f;i++) {
					cB=fc.read(byteb);
					byteb.flip();
					posting.getList().add(byteb.getInt());
					byteb.clear();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posting;
	}

	@Override
	public void writePosting(FileChannel fc, PostingList p) {
		/*
		 * TODO: Your code here
		 *       Write the given postings list to the given file.
		 */
		
		ByteBuffer byteb = ByteBuffer.allocate((4*2) + (4*p.getList().size()));

		byteb.putInt(p.getTermId());
		byteb.putInt(p.getList().size());

		for(int i:p.getList()) {
			byteb.putInt(i);
		}
		try {
			byteb.flip();
			fc.write(byteb);
			byteb.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

