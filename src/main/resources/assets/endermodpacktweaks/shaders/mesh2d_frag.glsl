#version 330 core

uniform vec4 color;

out vec4 FragColor;

void main()
{
    FragColor = color;
    //FragColor = vec4(1.0, 0.5, 0.5, 0.5);
}
