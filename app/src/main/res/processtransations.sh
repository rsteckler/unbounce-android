find . -type d -name "values-*" | while read file; do ./stripblanktags.sh "$file"; done
