package engine.audio;

import engine.utils.Utils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;

public class AudioBuffer {

    private int bufferId;

    public AudioBuffer(String fileName) {
        this.bufferId = alGenBuffers();
        this.loadSound(fileName);
    }

    private void loadSound(String fileName) {
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

            // send the data to OpenAL
            // buffers containing audio data with more than one channel will be played without 3D spatialization features
            alBufferData(this.bufferId, format, rawAudioBuffer, sampleRate);

            // free the memory allocated by STB
            MemoryUtil.memFree(rawAudioBuffer);
        }
    }

    public int getBufferId() {
        return this.bufferId;
    }

    public void cleanup() {
        alDeleteBuffers(this.bufferId);
    }
}
