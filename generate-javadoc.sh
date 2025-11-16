#!/bin/bash

# Script to generate Javadoc for RU Donuts project
# This will create documentation for all Java classes except Main.java

echo "Generating Javadoc..."

# Create javadoc directory if it doesn't exist
mkdir -p javadoc

# Generate Javadoc
javadoc -d javadoc \
  -sourcepath src/main/java \
  -subpackages org.example.model:org.example.controller \
  -exclude org.example.Main \
  -author \
  -version \
  -private

echo "Javadoc generated successfully in the 'javadoc' directory"
echo "Open javadoc/index.html to view the documentation"
