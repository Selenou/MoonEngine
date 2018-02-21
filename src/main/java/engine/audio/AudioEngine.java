package engine.audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioEngine {

    private HashMap<String, AudioSource> audioSources;
    private HashMap<String, AudioBuffer> audioBuffers;
    private AudioListener audioListener;
    private long device;
    private long context;

    public AudioEngine() {
        this.audioBuffers = new HashMap<>();
        this.audioSources = new HashMap<>();

        this.device = alcOpenDevice((ByteBuffer) null);

        if(this.device == NULL)
            throw new IllegalStateException("Failed to open the default device");

        this.context = alcCreateContext(this.device, (IntBuffer) null);

        if(this.context == NULL)
            throw new IllegalStateException("Failed to create an OpenAL context.");

        alcMakeContextCurrent(this.context);

        ALCCapabilities deviceCaps = ALC.createCapabilities(this.device);
        AL.createCapabilities(deviceCaps);

        this.audioListener = new AudioListener(new Vector3f());
    }

    public void addSoundBuffer(String name, AudioBuffer audioBuffer) {
        this.audioBuffers.put(name, audioBuffer);
    }

    public void addSoundSource(String name, AudioSource audioSource) {
        this.audioSources.put(name, audioSource);
    }

    public AudioSource getSoundSource(String name) {
        return this.audioSources.get(name);
    }

    public AudioBuffer getSoundBuffer(String name) {
        return this.audioBuffers.get(name);
    }

    public AudioListener getListener() {
        return this.audioListener;
    }

    /**
     * Terminate OpenAL
     */
    public void cleanup() {
        for(AudioSource source : this.audioSources.values())
            source.cleanup();

        for(AudioBuffer buffer : this.audioBuffers.values())
            buffer.cleanup();

        this.audioSources.clear();
        this.audioBuffers.clear();

        alcMakeContextCurrent(NULL);
        alcDestroyContext(this.context);
        alcCloseDevice(this.device);
    }
}
