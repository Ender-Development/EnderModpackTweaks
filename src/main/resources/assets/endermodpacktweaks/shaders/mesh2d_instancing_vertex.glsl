#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

// instance data
layout (location = 3) in vec3 posOffset;
layout (location = 4) in vec2 hudScale;
layout (location = 5) in vec2 hudOffset;
layout (location = 6) in float hudColor;

uniform mat4 modelView;
uniform mat4 projection;
uniform mat4 transformation;
uniform vec3 camPos;
uniform vec3 targetWorldPos;

vec4 unpackARGB(float f)
{
    float i = floatBitsToInt(f);

    float a = floor(mod(i / 16777216.0, 256.0));
    float r = floor(mod(i / 65536.0, 256.0));
    float g = floor(mod(i / 256.0, 256.0));
    float b = floor(mod(i, 256.0));

    return vec4(r, g, b, a) / 255.0;
}

flat out vec4 ColorOverride;

void main()
{
    vec3 adjustedPos = vec3(pos.x * hudScale.x + hudOffset.x, pos.y * hudScale.y + hudOffset.y, 0);
    vec4 transformed = transformation * vec4(adjustedPos, 1);
    transformed /= transformed.w;

    gl_Position = projection * modelView * vec4(targetWorldPos - camPos + transformed.xyz + posOffset, 1);

    if (hudColor != 0.0)
        ColorOverride = unpackARGB(hudColor);
    else
        ColorOverride = vec4(-1, -1, -1, -1);
}
