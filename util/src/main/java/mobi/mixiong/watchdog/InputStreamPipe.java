package mobi.mixiong.watchdog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
/**
 * Channels that wrap input streams are blocking, so this wrapper creates a
 * thread that pumps data from input streams, and presents and nonblocking
 * interface to the data.
 */
public class InputStreamPipe {
  final InputStream input;
  final Pipe pipe;
  final CopyThread copyThread;

  public InputStreamPipe(InputStream in) throws IOException {
    this.input = in;
    pipe = Pipe.open();

    copyThread = new CopyThread(in, pipe.sink());
  }

  public InputStreamPipe() throws IOException {
    this(System.in);
  }

  public void start() {
    copyThread.start();
  }

  public void shutdown() {
    copyThread.shutdown();
  }

  public SelectableChannel getChannel() throws IOException {
    SelectableChannel channel = pipe.source();

    channel.configureBlocking(false);

    return (channel);
  }

  protected void finalize() {
    copyThread.shutdown();
  }

  // ---------------------------------------------------

  public static class CopyThread extends Thread {
    volatile boolean keepRunning = true;
    InputStream in;
    WritableByteChannel out;

    CopyThread(InputStream in, WritableByteChannel out) {
      this.in = in;
      this.out = out;
      this.setDaemon(true);
    }

    public void shutdown() {
      keepRunning = false;
      this.interrupt();
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void run() {
      byte[] bytes = new byte[4096];
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      try {
        while (keepRunning) {
          int count = in.read(bytes);
          // 0 = nothing read
          // -1 = EOF
          if (count <= 0) {

            // don't burn cpu if there is no progress
            Clock.sleep(100);
            break;
          }

          buffer.clear().limit(count);

          out.write(buffer);
        }

        out.close();
      } catch (IOException e) {
        System.out.print("Input stream pipe closed");
        e.printStackTrace();
      } catch (InterruptedException e) {
        System.out.print("Input stream pipe interrupted");
        e.printStackTrace();
      }
    }
  }
}
