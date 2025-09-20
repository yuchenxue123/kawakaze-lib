package cute.neko.kawakaze.utils

class Identifier private constructor(
    val namespace: String,
    val path: String
) {

    companion object {
        fun of(namespace: String, path: String): Identifier {
            return ofValidated(namespace, path)
        }

        fun ofVanilla(path: String): Identifier {
            return Identifier("minecraft", validatePath("minecraft", path))
        }

        private fun ofValidated(namespace: String, path: String): Identifier {
            return Identifier(validateNamespace(namespace, path), validatePath(namespace, path))
        }

        fun isNamespaceValid(namespace: String): Boolean {
            return namespace.toCharArray().all { isNamespaceCharacterValid(it) }
        }

        private fun validateNamespace(namespace: String, path: String): String {
            if (!isNamespaceValid(namespace)) {
                throw InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: $namespace:$path")
            } else {
                return namespace
            }
        }

        fun isPathValid(path: String): Boolean {
            return path.toCharArray().all { isPathCharacterValid(it) }
        }

        private fun validatePath(namespace: String?, path: String): String {
            if (!isPathValid(path)) {
                throw InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: $namespace:$path")
            } else {
                return path
            }
        }

        private fun isNamespaceCharacterValid(character: Char): Boolean {
            return character == '_' || character == '-' || character in 'a'..'z' || character in '0'..'9' || character == '.'
        }

        private fun isPathCharacterValid(character: Char): Boolean {
            return character == '_' || character == '-' || character in 'a'..'z' || character in '0'..'9' || character == '/' || character == '.'
        }
    }

    override fun toString(): String {
        return "$namespace:$path"
    }
}