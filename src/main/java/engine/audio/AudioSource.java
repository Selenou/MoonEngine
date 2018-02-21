package engine.audio;

import engine.component.Component;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class AudioSource extends Component {

    private int sourceId;

    public AudioSource() {
        this.sourceId = alGenSources();
    }

    public void setBuffer(int bufferId) {
        this.stop();
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    public void play() {
        alSourcePlay(this.sourceId);
    }

    public void pause() {
        alSourcePause(this.sourceId);
    }

    public void stop() {
        alSourceStop(this.sourceId);
    }

    public void setVolume(float volume) {
        alSourcef(this.sourceId, AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        alSourcef(this.sourceId, AL_PITCH, pitch);
    }

    public void setPosition(Vector3f position) {
        alSource3f(this.sourceId, AL_POSITION, position.x(), position.y(), position.z());
    }

    public void setVelocity(Vector3f velocity) {
        alSource3f(this.sourceId, AL_POSITION, velocity.x(), velocity.y(), velocity.z());
    }

    public void setLooping(boolean loop) {
        alSourcei(this.sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
    }

    public boolean isPlaying() {
        return alGetSourcei(this.sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void cleanup() {
        this.stop();
        alDeleteBuffers(sourceId);
    }
}
