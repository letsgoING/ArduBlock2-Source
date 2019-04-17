ArduBlock
======

ArduBlock is a Block Programming Language for Arduino. The language and functions model closely to [Arduino Language Reference](http://arduino.cc/en/Reference/HomePage)


Installation
----
The project is managed by Maven. After checking out the source for the first time, one should run the following to install Arduino's pde.jar into the local repository. 

	$ mvn validate
	
	$ mvn clean package

Usage
----

	$ mvn exec:java -Dexec.mainClass="com.ardublock.Main"

Development
----
Change the /src/main/resources/com/ardublock/block/ardublock.xml to add new blocks to ArduBlock

	$ mvn clean package

	$ mvn compile exec:java -Dexec.mainClass="com.ardublock.Main"

The Visual Block environment should show up. Happy Hacking! ;) 


Eclipse
---------
mvn eclipse:eclipse

Deploy
----
Edit code of ardublock

	$ mvn clean package

copy the target/ardublock-all.jar to Arduino\tools\ArduBlockTool\tool

Authors
----
* David Li taweili@gmail.com
* HE Qichen heqichen@gmail.com


ToDo
----
* Integrate the [scripting engine](http://java.sun.com/developer/technicalArticles/J2SE/Desktop/scripting/) into the language blocks for code generation

License
----

Copyright (C) 2011 David Li and He Qichen

This file is part of ArduBlock.

ArduBlock is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ArduBlock is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with ArduBlock.  If not, see <http://www.gnu.org/licenses/>.



Changes by letsgoING
=====================
Contact: 	info@letsgoing.de
Source:		github.com/letsgoing

- changed Tool Name -> Context.java line 43
- added Zoom Buttons -> OpenBlocksFrame.java line 236-278
- added image processing for export -> OpenBlocksFrame.java line 142-175
- added LMS-Site Button
- added expert Mode Butten (Block-Menu-Switch) -> OpenBlocksFrame.java line 273-293
- added resetExpertState() fcn -> -> OpenBlocksFrame.java line 499-501
- added subroutine_var + subroutine_varret Blocks -> GenerateCodeButtonListener.java line 116-146
- added interrupt Blocks -> GenerateCodeButtonListener.java line 50 + 133-148 + 211-216
- added containsSetupCommand method -> Translator.java line 181-189
- Button Domain via ardublock.properties file
- removed "ABVAR_" Prefix and variable counter 
- reorganized ardublock.xml,  ardublock.properties, ardublock_de.properties and packages (menu structure)
- removed other language.properties-files (could be added again if needed)
- hack to remove \n at array declaration -> AutoFormat.java line 602-606
- removed \n after function declaration  -> AutoFormat.java line 845
- removed headerCommand for subroutines (now done in BlockFiles)-> Translator.java line 78-85
