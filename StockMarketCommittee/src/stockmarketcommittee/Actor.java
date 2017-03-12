/***
 * The Example is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Example is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Neuroph. If not, see <http://www.gnu.org/licenses/>.
 * 
 * This module is built on the following base:
 * http://sujitpal.blogspot.com/2008/12/java-concurrency-with-jetlang-actors.html
 */

package stockmarketcommittee;

import org.jetlang.channels.Channel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;

public abstract class Actor {

    private Channel<Message> inChannel;
    private Channel<Message> outChannel;
    private Fiber fiber;

    public Actor(Channel<Message> inChannel,
            Channel<Message> outChannel,
            Fiber fiber) {
        this.inChannel = inChannel;
        this.outChannel = outChannel;
        this.fiber = fiber;
    }

    public void start() {
        // set up subscription listener
        Callback<Message> onRecieve = new Callback<Message>() {

            public void onMessage(Message message) {
                act(message);
                if (outChannel != null) {
                    outChannel.publish(message);
                }
                // process poison pill, dispose current actor and pass the message
                // on to the next actor in the chain (above)
                if (message.payload instanceof String &&
                        Main.STOP.equals(message.payload)) {
                    fiber.dispose();
                }
            }
        };
        // subscribe to incoming channel
        inChannel.subscribe(fiber, onRecieve);
        // start the fiber
        fiber.start();
    }

    public abstract void act(Message message);
}


