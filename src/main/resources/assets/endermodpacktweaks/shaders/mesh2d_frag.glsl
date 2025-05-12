#version 330 core

uniform vec4 color;

out vec4 FragColor;

flat in vec4 ColorOverride;

void main()
{
    if (ColorOverride.x != -1 && ColorOverride.y != -1 && ColorOverride.z != -1 && ColorOverride.w != -1)
        FragColor = ColorOverride;
    else
        FragColor = color;
    //FragColor = vec4(1.0, 0.5, 0.5, 0.5);
}
