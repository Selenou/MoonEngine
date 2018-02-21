package engine.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class AudioListener {

    public AudioListener(Vector3f position) {
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public void setSpeed(Vector3f speed) {
        alListener3f(AL_VELOCITY, speed.x(), speed.y(), speed.z());
    }

    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
    }
}
