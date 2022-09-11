# libgenetics
Minecraft Fabric/Quilt 1.19.x Library Mod, made by DiamondDev


# Add to Project
## Repository Block (`build.gradle`)
```
repositories {
  maven { url 'https://jitpack.io' }
}
```

## Dependency Block(`build.gradle`)
```
dependencies {

  ## libGenetics
  modImplementation "com.github.diamonddevv:libgenetics:${project.libgenetics_version}"
  include "com.github.diamonddevv:libgenetics:${project.libgenetics_version}"
  
  ## Fabric ASM, used by libGenetics
  modImplementation "com.github.Chocohead:Fabric-ASM:${project.fasm_version}"
  include "com.github.Chocohead:Fabric-ASM:${project.fasm_version}"
  
  ## Fabric API, used by libGenetics (You probably have this anyway)
  modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
  
}
```

## Dependency Block (`gradle.properties`)
```
  fabric_version=0.60.0+1.19.2
  asm_version = v2.3
  libgenetics_version = 1.0.0
 ```
