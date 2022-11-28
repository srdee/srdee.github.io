wiwa_counts = c(2, 6)
dpois(x = wiwa_counts, lambda = 2)

lambdas = seq(.1, 5, by = 0.1)

for (l in 1:length(lambdas)) {
  vnew[l] <- sum(log(dpois(x = wiwa_counts, lambda = l)))
}

max = max(vnew)

lambdas[which(vnew==max)]