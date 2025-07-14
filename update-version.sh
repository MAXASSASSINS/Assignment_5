#!/bin/bash

# Usage: ./update-version.sh v2

NEW_VERSION=$1
FILE_LIST="file-list.txt"

# Check version input
if [ -z "$NEW_VERSION" ]; then
  echo "❌ Usage: ./update-version.sh <version>"
  echo "Example: ./update-version.sh v2"
  exit 1
fi

# Check if filelist.txt exists
if [ ! -f "$FILE_LIST" ]; then
  echo "❌ Required file 'filelist.txt' not found!"
  exit 1
fi

echo "🔄 Updating all :vX tags to :$NEW_VERSION in files listed in $FILE_LIST"

# Loop through each line in filelist.txt
while read -r file; do
  # Skip empty lines or comments
  [[ -z "$file" || "$file" =~ ^# ]] && continue

  if [ -f "$file" ]; then
    if sed -i -E "s|(:)v[0-9]+|\1$NEW_VERSION|g" "$file"; then
      echo "✅ Updated $file"
    else
      echo "❌ Failed to update $file (permission denied?)"
    fi
  else
    echo "⚠️  Skipped $file (not found)"
  fi
done < "$FILE_LIST"

echo "🎉 All image versions updated to :$NEW_VERSION"