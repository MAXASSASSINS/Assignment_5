#!/bin/bash

# Usage: ./update-version.sh v2

if [ -z "$1" ]; then
  echo "❌ Usage: ./update-version.sh <version-tag> (e.g., v2)"
  exit 1
fi

NEW_VERSION=$1
OLD_VERSION_PATTERN=":v[0-9]+"

echo "🔄 Updating all image tags to version: $NEW_VERSION"

# 1. Update all YAMLs in k8s/
find ./k8s -type f -name "*.yaml" | while read -r file; do
  if grep -q "image: " "$file"; then
    sed -i -E "s|(:)v[0-9]+|\1$NEW_VERSION|g" "$file"
    echo "✅ Updated $file"
  else
    echo "ℹ️  Skipped $file (no image tag found)"
  fi
done

# 2. Update ci_pipeline.sh
if [ -f "./ci_pipeline.sh" ]; then
  sed -i -E "s|(:)v[0-9]+|\1$NEW_VERSION|g" ./ci_pipeline.sh
  echo "✅ Updated ci_pipeline.sh"
fi

# 3. Update any other .sh files in root
for file in ./*.sh; do
  if [[ "$file" != "./update-version.sh" ]]; then
    sed -i -E "s|(:)v[0-9]+|\1$NEW_VERSION|g" "$file"
    echo "✅ Updated $file"
  fi
done

echo "🎉 All image versions updated to $NEW_VERSION"
