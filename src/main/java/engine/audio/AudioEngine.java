package engine.audio;

import engine.utils.Utils;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class AudioEngine {

    private HashMap<String, Integer> buffers;
    private long device;
    private long context;

    public AudioEngine() {
        // select the "preferred device"
        this.device = alcOpenDevice((ByteBuffer) null);

        if(this.device == NULL)
            throw new IllegalStateException("Failed to open the default device");

        // create context
        this.context = alcCreateContext(this.device, (IntBuffer) null);

        if(this.context == NULL)
            throw new IllegalStateException("Failed to create an OpenAL context.");

        alcMakeContextCurrent(this.context);

        ALCCapabilities deviceCaps = ALC.createCapabilities(this.device);
        AL.createCapabilities(deviceCaps);

        this.buffers = new HashMap<>();
    }

    private void setListenerPosition(Vector3f position){
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
    }

    public void loadSound(String fileName) {
        String path = Utils.getAbsolutePath("/audio/" + fileName);

        try (MemoryStack stack = MemoryStack.stackPush()) {

            // allocate space to store return information from the function
            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);

            int channels = channelsBuffer.get();
            int sampleRate = sampleRateBuffer.get();

            System.out.println("Loading audio " + fileName + " | channels:" + channels + ", sampleRate:" + sampleRate);

            // find the correct OpenAL format
            int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;

            int bufferPointer = alGenBuffers();
            this.buffers.put(fileName, bufferPointer);

            // send the data to OpenAL
            // buffers containing audio data with more than one channel will be played without 3D spatialization features
            alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

            // free the memory allocated by STB
            free(rawAudioBuffer);
        }
    }

    public int getSoundBuffer(String fileName) {
        if(!this.buffers.containsKey(fileName))
            throw new RuntimeException("Failed to get this sound buffer. Maybe it wasn't loaded");

        return this.buffers.get(fileName);
    }

    /**
     * Terminate OpenAL
     */
    public void cleanUp() {
        for(int buffer : this.buffers.values()) {
            alDeleteBuffers(buffer);
        }

        alcMakeContextCurrent(NULL);
        alcDestroyContext(this.context);
        alcCloseDevice(this.device);
    }
}
