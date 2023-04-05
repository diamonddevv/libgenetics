# libgenetics
Minecraft Fabric/Quilt 1.19.x Library Mod, made by DiamondDev

LibGenetics adds several helpful classes and utilities, along with a few named specialised packages such as Nerve (Networking) or Cognition (Server Data/Client Resource Loading).

The library uses GitHub commit hashes for versions. You can find a Python script on Replit to get the most recent hash on my website, or simply get it from the recent commits here.

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

  // libGenetics
  modImplementation "com.github.diamonddevv:libgenetics:${project.libgenetics_version}"
  include "com.github.diamonddevv:libgenetics:${project.libgenetics_version}"
  
  // Fabric ASM, used by libGenetics
  modImplementation "com.github.Chocohead:Fabric-ASM:${project.fasm_version}"
  include "com.github.Chocohead:Fabric-ASM:${project.fasm_version}"
  
  // Fabric API, used by libGenetics (You probably have this anyway)
  modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
  
}
```

## Dependency Block (`gradle.properties`)
```
  fabric_version= {latest should work}
  fasm_version = v2.3
  libgenetics_version = {version goes here}
 ```
