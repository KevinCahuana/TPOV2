        public Usuario mergeUsuario(Usuario usuario) {
            logger.info("Fusionando usuario con userId: {}", usuario::getUserId);
            Usuario merged = usuarioRepository.save(usuario);
            logger.info("Usuario fusionado en la base de datos con id: {}", merged::getId);
            return merged;
        }
