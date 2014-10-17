PLEASE BE SURE TO READ THIS ENTIRE DOCUMENT, AS IT DESCRIBES THE FORMAT AND REQUIREMENTS OF THE SAMPLE CODE
DOWNLOAD ZIP ARCHIVES. We hope you have fun with the sample code we have included and that you find it useful
and informative. Enjoy!

This ZIP archive contains code from every chapter and appendix of the book with the exception of those with
folders designating no code. Please also note that, in some cases, certain examples or listings from the
chapters are not included because the example is generated code, output, or something you would never enter
yourself. It is also possible to download the sample code for each chapter individually. If you have done this,
only the code for the chapter you downloaded is contained within this ZIP archive.

As of September 2014, the sample code includes IntelliJ IDEA 13 projects and Eclipse Luna 4.4 Java EE projects.
Remember that your book comes with a free 90-day IntelliJ IDEA 13 Ultimate personal license. We encourage you
to try it instead of using Eclipse, as you will get a better overall experience.

IMPORTANT NOTE: For the IntelliJ sample code you will need IntelliJ IDEA Ultimate 13.1.4 Build 135.1230 or
newer. For the Eclipse sample code you will need Eclipse Luna 4.4.0 Build 20140612-0600 for Java EE Developers
or newer. See the bottom of this file for critical Eclipse project opening instructions.

The layout of the ZIP archives for individual chapters looks like this, where XX is the chapter number (with
leading zeroes where applicable):

656464 cXX Code.zip
  |
  --Eclipse
      |
      --Sample Project 1
      --Sample Project n
  --IntelliJ
      |
      --Sample Project 1
      --Sample Project n

The only exceptions to this pattern are Chapter 2, which does not contain "IntelliJ" and "Eclipse" folders,
and Chapter 8, which contains files that aren't IDE projects IN ADDITION TO the standard "IntelliJ" and
"Eclipse" folders. The organization of these chapters' sample code is self-evident.

The ZIP archive for the entire book looks only slightly different:

9781118656464 Full Code.zip
  |
  --Chapter XX
      |
      --Eclipse
          |
          --Sample Project 1
          --Sample Project n
      --IntelliJ
          |
          --Sample Project 1
          --Sample Project n
  --Chapter XX
      |
      ...

Whether you download all the code for the entire book at once or individual chapters, not every chapter has
downloadable sample code. The following chapters DO NOT have sample code, so don't be alarmed when you cannot
find it:

Chapter 1
Chapter 19
Chapter 25

ECLIPSE PROJECT OPENING INSTRUCTIONS: Unlike IntelliJ IDEA, which allows you to simply open any project
ad-hoc with one button click, Eclipse requires that you have a workspace and add projects to your workspace
before you can open them. The details of working with workspaces are outside the scope of this document;
consult the Eclipse documentation for more details. When you open Eclipse, be sure you have a workspace
selected. Then, go to File -> Import. In the Import window, expand the General folder. Select Existing
Projects into Workspace and click Next. In the new window, select the directory that holds the sample project
in the Select Root Directory field. (For your purposes, this would be the Eclipse directory in the ZIP
archive you downloaded.) Check the check box beside the project you want to import, then click Finish. DO NOT
have more than one sample project from the book in your workspace at a time--Doing so could result in context
path conflicts that prevent the projects from running. Assuming no errors were encountered, the desired project
should now be part of the workspace, meaning you can edit and run it. You may have to reconnect your local JRE
and Tomcat server to each project (consult the Eclipse documentation and "Using Eclipse" in Chapter 2 for more
information on how to do this).