#version 330 core

layout (location = 0) in vec3 ndcPos;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

// instance data
layout (location = 3) in vec3 posOffset;
layout (location = 4) in vec3 scale;

uniform mat4 modelView;
uniform mat4 projection;
uniform float screenWidthHeightRatio;
uniform mat4 transformation;
uniform vec3 camPos;
uniform vec3 targetWorldPos;

//out vec2 TexCoord;
//out vec3 FragNormal;

void main()
{
    vec3 adjustedNdc = vec3(ndcPos.x * scale.x, ndcPos.y / screenWidthHeightRatio * scale.y, 0);

    vec4 transformed = transformation * vec4(adjustedNdc, 1);
    transformed /= transformed.w;

    gl_Position = projection * modelView * vec4(targetWorldPos - camPos + transformed.xyz + posOffset, 1);

    //TexCoord = texCoord;
    //FragNormal = normal;
}
